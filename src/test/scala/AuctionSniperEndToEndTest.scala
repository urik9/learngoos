import org.specs2.mutable.{After, SpecificationWithJUnit}
import org.specs2.specification.Scope


class AuctionSniperEndToEndTest extends SpecificationWithJUnit {
  sequential

  trait ApplicationAndAuction extends Scope with After {
    val auction = new FakeAuctionServer("item54321")
    val application = new ApplicationRunner
  }

  "Sniper" should {
    "join auction and lose" in new ApplicationAndAuction {
      auction.startSellingItem()
      application.startBiddingIn(auction)
      auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID)
      auction.announceClosed()
      application.showsSniperLost(auction)

      override def after = {
        auction.stop()
        application.stop()
      }
    }
    "join auction, bid and lose" in new ApplicationAndAuction {
      auction.startSellingItem()
      application.startBiddingIn(auction)
      auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID)
      auction.reportPrice(1000, 98, "other bidder")
      application.showsSniperIsBiddingIn()
      auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID)
      auction.announceClosed()
      application.showsSniperLost(auction)


      override def after = {
        auction.stop()
        application.stop()
      }
    }
  }
}

