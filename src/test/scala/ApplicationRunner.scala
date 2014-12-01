import ApplicationRunner._
import SniperState._
import SniperTableModel.textFor
import org.specs2.matcher.MustMatchers


/**
 * Created by Uri_Keinan on 10/7/14.
 */


class ApplicationRunner extends MustMatchers{
  var itemId: String = _

  def arguments(): List[String] = XMPP_HOSTNAME :: SNIPER_ID :: SNIPER_PASSWORD :: ITEM_ID :: Nil

  def startBiddingIn(auction:FakeAuctionServer) = {
    itemId = auction.itemId
    val thread:Thread = new Thread("Test Application") {
      override def run() {
        new Main(arguments(): _*)
      }
    }
    thread.setDaemon(true)
    thread.start()
    driver = new AuctionSniperDriver(1000)
    driver.hasTitle(MainWindow.MAIN_WINDOW_NAME)
    driver.hasColumnTitles()
  }



  def hasShowsSniperIsBidding(lastPrice:Int, lastBid:Int) {
    driver.showsSniperStatus(itemId, lastPrice, lastBid, textFor(BIDDING))
  }
  def hasShowsSniperHasWonAuction(lastPrice:Int){
    driver.showsSniperStatus(itemId, lastPrice, lastPrice, textFor(WON))
  }

  def hasShownSniperIsWinning(winningBid:Int){
    driver.showsSniperStatus(itemId, winningBid, winningBid, textFor(WINNING))
  }

  def hasShowsSniperLost(auction: FakeAuctionServer, lastPrice:Int, lastBid:Int) {
   driver.showsSniperStatus(itemId, lastPrice, lastBid, textFor(LOST))
  }

  def stop(){
    if(driver != null){
      driver.dispose()
    }
  }
  var driver:AuctionSniperDriver = _
}

object ApplicationRunner {
  val SNIPER_ID = "sniper"
  val SNIPER_PASSWORD = "sniper"
  val XMPP_HOSTNAME = "localhost"
  val SNIPER_XMPP_ID = SNIPER_ID + "@" + XMPP_HOSTNAME + "/Auction"
  var ITEM_ID = "item54321"
}




