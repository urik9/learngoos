import MainWindow._
import org.specs2.matcher.MustMatchers
import ApplicationRunner._


/**
 * Created by Uri_Keinan on 10/7/14.
 */


class ApplicationRunner extends MustMatchers{
  def showsSniperHasWonAuction(){}

  def hasShownSniperIsWinning(){}


  def arguments(): List[String] = XMPP_HOSTNAME :: SNIPER_ID :: SNIPER_PASSWORD :: ITEM_ID :: Nil

  def startBiddingIn(auction:FakeAuctionServer) = {
    val thread:Thread = new Thread("Test Application") {
      override def run() {
        new Main(arguments(): _*)
      }
    }
    thread.setDaemon(true)
    thread.start()
    driver = new AuctionSniperDriver(1000)
    driver.showsSniperStatus(STATUS_JOINING)
  }



  def showsSniperIsBiddingIn() {
    driver.showsSniperStatus(MainWindow.STATUS_BIDDING)
  }

  def showsSniperLost(auction: FakeAuctionServer) {
    driver.showsSniperStatus(STATUS_LOST)
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




