import com.wixpress.common.specs2.JMock
import org.specs2.matcher.Scope
import org.specs2.mutable.Specification

/**
 * Created by Uri_Keinan on 11/10/14.
 */
class SniperListenerTest extends Specification with JMock {

  trait TestScope extends Scope {
  }

  val sniperListener: SniperListener = mock[SniperListener]
  val sniper: AuctionSniper = new AuctionSniper(null, sniperListener)

  "SniperListener" should {
    "reports lost when auction closes" in new TestScope {
      checking {
        atLeast(1).of(sniperListener).sniperLost
      }
      sniper.auctionClosed
    }
  }
}


