import org.specs2.mutable.{After, SpecificationWithJUnit}
import org.specs2.specification.Scope


class AuctionSniperEndToEndTest extends SpecificationWithJUnit {
  sequential

  trait ApplicationAndAuction extends Scope with After {
    val auction = new FakeAuctionServer("item54321")
    val application = new ApplicationRunner

    def after = {
      auction.stop()
      application.stop()
    }
  }

  "Sniper" should {
    "join auction and lose" in new ApplicationAndAuction {
      auction.startSellingItem()
      application.startBiddingIn(auction)
      auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID)
      auction.announceClosed()
      application.showsSniperLost(auction, 0, 0)
    }
    "join auction, bid and lose" in new ApplicationAndAuction {
      auction.startSellingItem()
      application.startBiddingIn(auction)
      auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID)
      auction.reportPrice(1000, 98, "other bidder")
      application.showsSniperIsBidding(1000, 1098)
      auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID)
      auction.announceClosed()
      application.showsSniperLost(auction, 1000, 1098)

    }
    "join auction bid and win" in new ApplicationAndAuction {
      auction.startSellingItem()
      application.startBiddingIn(auction)
      auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID)
      auction.reportPrice(1000, 98, "other bidder")
      application.showsSniperIsBidding(1000, 1098)
      auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID)
      auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID)
      application.hasShownSniperIsWinning(1098)
      auction.announceClosed()
      application.showsSniperHasWonAuction(1098)

    }
  }
}

