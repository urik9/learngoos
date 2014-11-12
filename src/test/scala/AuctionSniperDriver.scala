import com.objogate.wl.swing.AWTEventQueueProber
import com.objogate.wl.swing.driver.ComponentDriver._
import com.objogate.wl.swing.driver._
import com.objogate.wl.swing.gesture.GesturePerformer
import org.hamcrest.Matchers.equalTo


/**
 * Created by Uri_Keinan on 10/15/14.
 */
class AuctionSniperDriver(timeoutMillis: Int) extends JFrameDriver (
  new GesturePerformer(),
  JFrameDriver.topLevelFrame(named(Main.MAIN_WINDOW_NAME), showingOnScreen()),
  new AWTEventQueueProber(timeoutMillis, 100)) {

    def showsSniperStatus(statusText: String){
      new JLabelDriver(this, named(Main.SNIPER_STATUS_NAME)).hasText(equalTo(statusText))
    }

  }



