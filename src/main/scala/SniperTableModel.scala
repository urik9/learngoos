import javax.swing.table.AbstractTableModel

import SniperState._
import SniperTableModel._


/**
  * Created by Uri_Keinan on 11/13/14.
 */
object SniperTableModel {
  val STARTING_UP: SniperSnapshot = new SniperSnapshot("", 0, 0, JOINING)
  var STATUS_TEXT = Seq("Joining", "Bidding", "Winning", "Lost", "Won")
  var snapshot = STARTING_UP

  def textFor(state: SniperState):String = STATUS_TEXT(state.id)
}


class SniperTableModel extends AbstractTableModel {
  def sniperStateChanged(newSnapshot: SniperSnapshot) = {
    snapshot = newSnapshot
    fireTableRowsUpdated(0, 0)
  }

  def getColumnCount(): Int = {
    Column.values.size
  }

  def getRowCount: Int = {
    1
  }

  def getValueAt(rowIndex: Int, columnIndex: Int): AnyRef = {
    Column(columnIndex) match {
      case Column.ITEM_IDENTIFIER => snapshot.itemId
      case Column.LAST_PRICE => snapshot.lastPrice.toString
      case Column.LAST_BID => snapshot.lastBid.toString
      case Column.SNIPER_STATE => textFor(snapshot.state)
    }
  }

}
object Column extends Enumeration {
  type Column = Value
  val ITEM_IDENTIFIER, LAST_PRICE, LAST_BID, SNIPER_STATE = Value

  val columnTitles = Map(
    ITEM_IDENTIFIER -> "Item Id",
    LAST_PRICE -> "Last Price",
    LAST_BID -> "Last Bid",
    SNIPER_STATE -> "State"
  )

}
