import javax.swing.SwingUtilities

/**
 * Created by Uri_Keinan on 11/10/14.
 */
class SniperStateDisplayer(ui: MainWindow) extends SniperListener {

  override def sniperBidding(){
    showStatus(MainWindow.STATUS_BIDDING)
  }

  override def sniperLost(){
    showStatus(MainWindow.STATUS_LOST)
  }

  def showStatus(status:String){
    SwingUtilities.invokeLater(new Runnable {
      override def run(): Unit = ui.showStatus(status)
    })
  }


}
