import com.wixpress.common.specs2.JMock
import org.specs2.matcher.Scope
import org.specs2.mutable.Specification

/**
 * Created by Uri_Keinan on 11/10/14.
 */
class AuctionSniperTest extends Specification with JMock {
  val auction: Auction = mock[Auction]
  val sniperListener: SniperListener = mock[SniperListener]
  val sniper: AuctionSniper = new AuctionSniper(auction, sniperListener)

  trait TestScope extends Scope {

  }

  "AuctionSniper" should {
    "report lost when auction closes" in new TestScope {
      checking {
        atLeast(1).of(sniperListener).sniperLost()
      }
        sniper.auctionClosed()
    }


    "bid higher and report when new price arrives" in {
      val price = 1001
      val increment = 25
      val bid = price + increment
      checking {
       oneOf(auction).bid(bid)
        atLeast(1).of(sniperListener).sniperBidding()
      }
      sniper.currentPrice(price, increment, PriceSource.FromOtherBidder)
    }

    "report is winning when current price is from sniper" in {
      checking {
        atLeast(1).of(sniperListener).sniperWinning()
      }
      sniper.currentPrice(123, 45, PriceSource.FromSniper)
    }

  }



}
