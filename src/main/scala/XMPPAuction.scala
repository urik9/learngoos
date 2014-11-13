import org.jivesoftware.smack.Chat
import Main._

/**
 * Created by Uri_Keinan on 11/11/14.
 */
class XMPPAuction(chat: Chat) extends Auction {
  override def bid(amount: Int){
    sendMessage(BID_COMMAND_FORMAT format amount)
  }

  override def join(){
    sendMessage(JOIN_COMMAND_FORMAT)
  }

  def sendMessage(message: String) = chat.sendMessage(message)

}
