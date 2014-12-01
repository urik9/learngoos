import javax.swing.table.AbstractTableModel

import SniperState._
import SniperTableModel._


/**
 * Created by Uri_Keinan on 11/13/14.
 */

class SniperTableModel extends AbstractTableModel with SniperListener {
  def sniperStateChanged(newSnapshot: SniperSnapshot) = {
    snapshot = newSnapshot
    fireTableRowsUpdated(0, 0)
  }

  override def getColumnName(column: Int): String = Column(column).columnName


  def getColumnCount(): Int = {
    Column.values.size
  }

  def getRowCount: Int = {
    1
  }

  def getValueAt(rowIndex: Int, columnIndex: Int): AnyRef = {
    Column(columnIndex).valueIn(snapshot)
  }

}
object SniperTableModel {
  val STARTING_UP: SniperSnapshot = new SniperSnapshot("", 0, 0, JOINING)
  var STATUS_TEXT = Seq("Joining", "Bidding", "Winning", "Lost", "Won")
  var snapshot = STARTING_UP

  def textFor(state: SniperState): String = STATUS_TEXT(state.id)
}

case class Column(valueIn: SniperSnapshot => AnyRef, columnName:String) {
  def id = Column.values.indexOf(this)
}

object Column {
  val values = Seq(ItemIdentifier, LastPrice, LastBid, State)

  def apply(id: Int) = values(id)
}

object ItemIdentifier extends Column(_.itemId, "Item")

object LastPrice extends Column(s => s.lastPrice.underlying(), "Last Price")

object LastBid extends Column(s => s.lastBid.underlying(), "Last Bid")

object State extends Column(s => textFor(s.state), "State")
