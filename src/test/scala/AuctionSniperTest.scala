import SniperState._
import com.wixpress.common.specs2.JMock
import org.jmock.States
import org.specs2.matcher.{Matcher, Scope}
import org.specs2.mutable.Specification
import ApplicationRunner.ITEM_ID
/**
 * Created by Uri_Keinan on 11/10/14.
 */
class AuctionSniperTest extends Specification with JMock {

  val auction: Auction = mock[Auction]
  val sniperListener: SniperListener = mock[SniperListener]
  val sniper: AuctionSniper = new AuctionSniper(ITEM_ID, auction, sniperListener)
  val sniperStates: States = states("sniper")

  trait TestScope extends Scope {
    def aSniperThatIs(state: SniperState.Value): Matcher[SniperSnapshot] = ((_:SniperSnapshot).state == state, s"Sniper is not $state")
  }

  "AuctionSniper" should {
    "report lost when auction closes immediately" in new TestScope {
      checking {
        atLeast(1).of(sniperListener).sniperStateChanged(`with`(aSniperThatIs(LOST)))
      }
        sniper.auctionClosed()
    }

    "reports lost when auction closes while bidding" in new TestScope {
      checking {
        ignoring(auction)
        allowing(sniperListener).sniperStateChanged(`with`(aSniperThatIs(BIDDING))); `then` (sniperStates.is("bidding"))
        atLeast(1).of(sniperListener).sniperStateChanged(`with`(aSniperThatIs(LOST))); when(sniperStates.is("bidding"))
      }
      sniper.currentPrice(123, 45, PriceSource.FromOtherBidder)
      sniper.auctionClosed()
    }



    "bid higher and report when new price arrives" in {
      val price = 1001
      val increment = 25
      val bid = price + increment
      checking {
       oneOf(auction).bid(bid)
        atLeast(1).of(sniperListener).sniperStateChanged(SniperSnapshot(ITEM_ID, price, bid, BIDDING))
      }
      sniper.currentPrice(price, increment, PriceSource.FromOtherBidder)
    }

    "report is winning when current price is from sniper" in new TestScope {
      checking {
        ignoring(auction)
        allowing(sniperListener).sniperStateChanged(`with`(aSniperThatIs(BIDDING)));`then`(sniperStates.is("bidding"))
        atLeast(1).of(sniperListener).sniperStateChanged(new SniperSnapshot(ITEM_ID, 135, 135, WINNING));when(sniperStates.is("bidding"))
      }
      sniper.currentPrice(123, 12, PriceSource.FromOtherBidder)
      sniper.currentPrice(135, 25, PriceSource.FromSniper)
    }

    "reports won if auction closed when winning" in new TestScope {
      checking{
        ignoring(auction)
        allowing(sniperListener).sniperStateChanged(`with`(aSniperThatIs(WINNING))); `then`(sniperStates.is("winning"))
        atLeast(1).of(sniperListener).sniperStateChanged(`with`(aSniperThatIs(WON))); when(sniperStates.is("winning"))
      }
      sniper.currentPrice(123, 45, PriceSource.FromSniper)
      sniper.auctionClosed()
    }

  }



}
