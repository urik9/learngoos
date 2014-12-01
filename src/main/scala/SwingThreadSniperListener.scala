import javax.swing.SwingUtilities

/**
 * Created by Uri_Keinan on 11/10/14.
 */
class SwingThreadSniperListener(snipers: SniperTableModel) extends SniperListener {

  override def sniperStateChanged(snapshot: SniperSnapshot): Unit ={
    SwingUtilities.invokeLater(new Runnable {
      override def run(): Unit = snipers.sniperStateChanged(snapshot)
    })
  }

}
