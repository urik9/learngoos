import javax.swing._
import javax.swing.border.LineBorder
import MainWindow._
import java.awt.Color


/**
 * Created by Uri_Keinan on 10/22/14.
 */
class MainWindow() extends JFrame {
  val sniperStatus = createLabel(STATUS_JOINING)


  setName(MAIN_WINDOW_NAME)
  add(sniperStatus)
  pack()
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  setVisible(true)

  def createLabel(initialText: String): JLabel = {
    val result: JLabel = new JLabel(initialText)
    result.setName(SNIPER_STATUS_NAME)
    result.setBorder(new LineBorder(Color.BLACK))
    result
  }

  def showStatus(status: String):Unit = {
    sniperStatus.setText(status)
  }


}

  object MainWindow {
    val STATUS_BIDDING: String = "Bidding"
    val MAIN_WINDOW_NAME = "Auction Sniper"
    val SNIPER_STATUS_NAME = "sniper-status"
    val STATUS_JOINING: String = "Joining"
    val STATUS_LOST: String = "Lost"
    val STATUS_WINNING: String = "Winning"
    val STATUS_WON: String = "Won"


  }







