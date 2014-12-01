import javax.swing._
import javax.swing.border.LineBorder
import MainWindow._
import java.awt.{BorderLayout, Container, Color}


/**
  * Created by Uri_Keinan on 10/22/14.
 */
class MainWindow(snipers: SniperTableModel) extends JFrame(APPLICATION_TITLE) {



  setName(MAIN_WINDOW_NAME)
  fillContentPane(makeSniperTable())
  pack()
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  setVisible(true)

  def sniperStateChanged(sniperState: SniperSnapshot) = {
    snipers.sniperStateChanged(sniperState)
  }

  def fillContentPane(snipersTable: JTable) = {
    val contentPane: Container = getContentPane
    contentPane.setLayout(new BorderLayout())
    contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER)
  }

  def makeSniperTable(): JTable = {
    val snipersTable = new JTable(snipers)
    snipersTable.setName(SNIPER_TABLE_NAME)
    snipersTable
  }

  def createLabel(initialText: String): JLabel = {
    val result: JLabel = new JLabel(initialText)
    result.setName(SNIPER_STATUS_NAME)
    result.setBorder(new LineBorder(Color.BLACK))
    result
  }


}

object MainWindow {
  val APPLICATION_TITLE = "Auction Sniper"
  val SNIPER_TABLE_NAME: String = "Snipers Table"
  val MAIN_WINDOW_NAME = "Auction Sniper"
  val SNIPER_STATUS_NAME = "sniper-status"



}







