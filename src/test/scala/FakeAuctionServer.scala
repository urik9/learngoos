import java.util.concurrent.{ArrayBlockingQueue, TimeUnit}

import org.jivesoftware.smack._
import org.jivesoftware.smack.packet.Message
import org.specs2.matcher.{Matcher, MustMatchers}


/**
 * Created by Uri_Keinan on 10/7/14.
 */
class FakeAuctionServer(val itemId: String) extends MustMatchers {


  val ITEM_ID_AS_LOGIN: String = "auction-%s"
  val AUCTION_RESOURCE: String = "Auction"
  val XMPP_HOSTNAME: String = "localhost"
  val AUCTION_PASSWORD: String = "auction"
  val connection = new XMPPConnection(XMPP_HOSTNAME)
  var currentChat: Chat = _
  val messageListener = new SingleMessageListener()



  def startSellingItem() {
    connection.connect()
    connection.login(String.format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD, AUCTION_RESOURCE)
    connection.getChatManager.addChatListener(new ChatManagerListener() {
      def chatCreated(chat: Chat, createdLocally: Boolean): Unit = {
        currentChat = chat
        chat.addMessageListener(messageListener)
      }
    })
  }
  def reportPrice(price: Int, increment: Int, bidder: String) {
    currentChat.sendMessage(f"SOLVersion: 1.1; Event: PRICE; CurrentPrice: $price%d; Increment: $increment%d; Bidder: $bidder%s;")
  }


  def hasReceivedJoinRequestFromSniper(sniperId: String) {
    receivesAMessageMatching(sniperId, beEqualTo(Main.JOIN_COMMAND_FORMAT))
  }


  def hasReceivedBid(bid: Int, sniperId: String) = {
    receivesAMessageMatching(sniperId, equalTo (Main.BID_COMMAND_FORMAT.format (bid)))
  }

  def announceClosed() {
    currentChat.sendMessage("SOLVersion: 1.1; Event: CLOSE;")
  }


  def stop() {
    connection.disconnect()
  }

  def getItemId():String = {
    itemId
  }
  def receivesAMessageMatching(sniperId: String, messageMatcher: Matcher[String]){
    messageListener.receivesAMessage(messageMatcher)
    currentChat.getParticipant must beEqualTo(sniperId)
  }




  class SingleMessageListener extends MessageListener {
    val messages = new ArrayBlockingQueue[Message](1)

    def processMessage(chat: Chat, message: Message) {
      messages.add(message)
    }

    def receivesAMessage(messageMatcher: Matcher[String]) {
      val message = messages.poll(5, TimeUnit.SECONDS)
      message  must not beNull;
      message.getBody must(messageMatcher)
    }
  }

}

