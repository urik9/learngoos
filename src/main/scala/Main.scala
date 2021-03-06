import java.awt.event.{WindowAdapter, WindowEvent}
import javax.swing.SwingUtilities

import Main._
import org.jivesoftware.smack.{Chat, XMPPConnection}


/**
 * Created by Uri_Keinan on 10/20/14.
 */





class Main (args: String*) extends {
  val snipers = new SniperTableModel
  val connection: XMPPConnection = connectTo(args(ARG_HOSTNAME), args(ARG_USERNAME), args(ARG_PASSWORD))
  var notToBeGCd: Chat = _
  var ui: MainWindow = _

  startUserInterface()
  joinAuction(connection, args(ARG_ITEM_ID))

  def startUserInterface() = {
    SwingUtilities.invokeAndWait(new Runnable {
      override def run(): Unit = {
        ui = new MainWindow(snipers)
      }
    })
  }


  def joinAuction(connection: XMPPConnection, itemId: String) = {
    disconnectWhenUICloses(connection)
    val chat: Chat = connection.getChatManager.createChat(
    auctionId(itemId, connection), null)
    this.notToBeGCd = chat
    val auction: Auction = new XMPPAuction(chat)
    chat.addMessageListener(new AuctionMessageTranslator(connection.getUser, new AuctionSniper(itemId, auction, new SwingThreadSniperListener(snipers))))
    auction.join()
  }


  def disconnectWhenUICloses(connection: XMPPConnection) {
    ui.addWindowListener(new WindowAdapter() {
      override def windowClosed(windowEvent: WindowEvent) {
        connection.disconnect()
      }
    })
  }


  def connectTo(hostname: String, username: String, password: String): XMPPConnection = {
    val con: XMPPConnection = new XMPPConnection(hostname)
    con.connect()
    con.login(username, password, AUCTION_RESOURCE)
    con

  }


  def auctionId(itemId: String, connection: XMPPConnection): String = {
    String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName())
  }


  def currentPrice(price: Int, increment: Int): Unit = {}

}

object Main{
  val BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN; "

  val JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;"

  def main(args: Array[String]) {
    new Main()
  }
  val SNIPER_STATUS_NAME = "sniper-status"
  val ARG_HOSTNAME = 0
  val ARG_USERNAME = 1
  val ARG_PASSWORD = 2
  val ARG_ITEM_ID = 3
  val MAIN_WINDOW_NAME = "Auction Sniper"


  val AUCTION_RESOURCE = "Auction"
  val ITEM_ID_AS_LOGIN = "auction-%s"
  val AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE

}


