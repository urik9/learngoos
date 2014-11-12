import java.util.EventListener

import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.{Chat, MessageListener}
/**
 * Created by Uri_Keinan on 11/3/14.
 */


class AuctionMessageTranslator(listener: AuctionEventListener) extends MessageListener {

  def processMessage(chat: Chat, message: Message) = {
    val event = AuctionEvent.from(message.getBody)
    if (event.eventType equals "CLOSE") {
      listener.auctionClosed()
    } else if (event.eventType equals "PRICE") {
      listener.currentPrice (event.currentPrice(), event.increment())
    }
  }

  class AuctionEvent{
    val fields = scala.collection.mutable.Map[String, String]()
    def eventType():String = {get("Event")}
    def currentPrice(): Int = {getInt("CurrentPrice")}
    def increment(): Int = {getInt("Increment")}

    def getInt(fieldName: String) = {get(fieldName).toInt}
    def get(fieldName: String) = {fields.get(fieldName).get}



    def addField(field: String){
      val pair: Array[String] = field.split(":")
      fields += pair(0).trim ->  pair(1).trim
    }
  }

  object AuctionEvent{
    def from(messageBody: String) = {
      val event: AuctionEvent = new AuctionEvent()
      messageBody.split(";").foreach(field => event.addField(field))
      event
    }
  }




}

trait AuctionEventListener extends EventListener {
  def currentPrice(price: Int, increment: Int): Unit
  def auctionClosed()

}
