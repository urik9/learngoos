import SniperState._
import SniperTableModel.textFor

import org.specs2.mutable.Specification

/**
 * Created by Uri_Keinan on 11/30/14.
 */
class ColumnTest extends Specification{
  "A Column" should {
    "return the correct fields" in {
      val snapshot = new SniperSnapshot("12345", 123, 45, BIDDING)
      ItemIdentifier.valueIn(snapshot) must be equalTo "12345"
      LastPrice.valueIn(snapshot).asInstanceOf[Int] must be equalTo 123
      LastBid.valueIn(snapshot).asInstanceOf[Int] must be equalTo 45
      State.valueIn(snapshot) must be equalTo textFor(BIDDING)
    }
  }
}
