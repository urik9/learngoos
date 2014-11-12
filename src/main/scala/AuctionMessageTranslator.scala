import java.util.EventListener

import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.{Chat, MessageListener}


/**
 * Created by Uri_Keinan on 11/3/14.
 */
class AuctionMessageTranslator(listener: AuctionEventListener) extends MessageListener {
  val event = scala.collection.mutable.Map[String, String]()

  def processMessage(chat: Chat, message: Message) = {
    getMessageParams(message)

    val eventType:String = event.get("Event").get
    if (eventType equals "CLOSE") {
      listener.auctionClosed()
    } else if (eventType equals "PRICE") {
      listener.currentPrice(Integer.parseInt(event.get("CurrentPrice").get), Integer.parseInt(event.get("Increment").get))
    }

    //
    //      case "CLOSE" => listener.auctionClosed()
    //      case "PRICE" => listener.currentPrice(Integer.parseInt(event.get("CurrentPrice").toString),Integer.parseInt(event.get("Increment").toString))
  }


  def getMessageParams(message: Message) = {
    val fields: Array[String] = message.getBody.split(";")
    for (field <- fields) {
      val pair: Array[String] = field.split(":")
      event += pair(0).trim -> pair(1).trim
    }
    event
  }

}

trait AuctionEventListener extends EventListener {
  def currentPrice(price: Int, increment: Int): Unit
  def auctionClosed()

}
