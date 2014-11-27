import javax.swing.event.{TableModelEvent, TableModelListener}

import SniperTableModel._
import com.wixpress.common.specs2.JMock
import org.specs2.matcher.Matcher
import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import org.specs2.mutable.Before
import Column._
import SniperState._

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
      checking{
        oneOf(listener).tableChanged(`with`(aRowChangedEvent(0)))
      }
      model.sniperStateChanged(new SniperSnapshot("itemId", 555, 666, BIDDING))

      assertColumnEquals(Column.ITEM_IDENTIFIER, "itemId")
      assertColumnEquals(Column.LAST_PRICE, "555")
      assertColumnEquals(Column.LAST_BID, "666")
      assertColumnEquals(Column.SNIPER_STATE, textFor(snapshot.state))
    }

  }
  def assertColumnEquals(column: Column, expected: Any) {
    val rowIndex = 0
    val columnIndex: Int = column.id
    expected must be equalTo model.getValueAt(rowIndex, columnIndex)
  }
  def aRowChangedEvent(row: Int): Matcher[TableModelEvent] = {
    (e: TableModelEvent) => (e.getSource == model && e.getFirstRow == row && e.getLastRow == row && e.getType == TableModelEvent.UPDATE, "not a change in row " + row)
  }
}
