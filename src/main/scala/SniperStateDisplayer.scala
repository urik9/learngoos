import javax.swing.SwingUtilities

/**
 * Created by Uri_Keinan on 11/10/14.
 */
class SniperStateDisplayer(ui: MainWindow) extends SniperListener {

  override def sniperStateChanged(snapshot: SniperSnapshot): Unit ={
    SwingUtilities.invokeLater(new Runnable {
      override def run(): Unit = ui.sniperStateChanged(snapshot)
    })
  }

}
