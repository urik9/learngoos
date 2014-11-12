import com.wixpress.common.specs2.JMock
import org.jivesoftware.smack.Chat
import org.jivesoftware.smack.packet.Message
import org.specs2.mutable.Specification


/**
 * Created by Uri_Keinan on 11/3/14.
 */
class AuctionMessageTranslatorTest extends Specification with JMock {
  useClassImposterizer()

  val UNUSED_CHAT: Chat = null
  val listener =  mock[AuctionEventListener]
  val translator: AuctionMessageTranslator = new AuctionMessageTranslator(listener)



  "ActionMessageTranslator " should {
     isolated
    "notify auction closed when close message received" in {
      checking {
        oneOf(listener).auctionClosed()
      }

      val message = new Message()
      message.setBody("SOLVersion: 1.1; Event: CLOSE;")

      translator.processMessage(UNUSED_CHAT, message)
    }

    "notifies bid details when current price message received" in {
      checking {
        exactly(1).of(listener).currentPrice(192, 7)
      }

      val message: Message = new Message()
      message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;")
      translator.processMessage(UNUSED_CHAT, message)
    }
  }

}
