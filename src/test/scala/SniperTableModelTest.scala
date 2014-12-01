import javax.swing.event.{TableModelEvent, TableModelListener}

import SniperState._
import SniperTableModel._
import com.wixpress.common.specs2.JMock
import org.specs2.matcher.Matcher
import org.specs2.mutable.{Before, Specification}
import org.specs2.specification.Scope

/**
* Created by Uri_Keinan on 11/16/14.
*/
class SniperTableModelTest extends Specification with JMock {

  val listener: TableModelListener = mock[TableModelListener]
  val model: SniperTableModel = new SniperTableModel()

  trait TestScope extends Scope with Before {
    override def before = model.addTableModelListener(listener)
  }


  "Sniper Table Model" should {
    "have enough columns" in new TestScope {
      model.getColumnCount must be equalTo Column.values.size
    }

    "sets sniper values in columns" in new TestScope {
      checking {
        oneOf(listener).tableChanged(`with`(aRowChangedEvent(0)))
      }
      model.sniperStateChanged(new SniperSnapshot("itemId", 555, 666, BIDDING))

      model.getValueAt(1, ItemIdentifier.id) must be equalTo snapshot.itemId
      model.getValueAt(1, LastPrice.id) must be equalTo snapshot.lastPrice.underlying()
      model.getValueAt(1, LastBid.id) must be equalTo snapshot.lastBid.underlying()
      model.getValueAt(1, State.id) must be equalTo textFor(snapshot.state)

    }

    def aRowChangedEvent(row: Int): Matcher[TableModelEvent] = {
      (e: TableModelEvent) => (e.getSource == model && e.getFirstRow == row && e.getLastRow == row && e.getType == TableModelEvent.UPDATE, "not a change in row " + row)
    }
  }
}