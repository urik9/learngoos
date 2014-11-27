import com.objogate.wl.swing.AWTEventQueueProber
import com.objogate.wl.swing.driver.ComponentDriver._
import com.objogate.wl.swing.driver._
import com.objogate.wl.swing.gesture.GesturePerformer
import com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText
import com.objogate.wl.swing.matcher.IterableComponentsMatcher.matching

/**
 * Created by Uri_Keinan on 10/15/14.
 */
class AuctionSniperDriver(timeoutMillis: Int) extends JFrameDriver (
  new GesturePerformer(),
  JFrameDriver.topLevelFrame(named(Main.MAIN_WINDOW_NAME), showingOnScreen()),
  new AWTEventQueueProber(timeoutMillis, 100)) {

    def showsSniperStatus(itemId:String, lastPrice:Int, lastBid:Int, statusText: String){
      new JTableDriver(this).hasRow(matching(
        withLabelText(itemId),withLabelText(lastPrice.toString),
        withLabelText(lastBid.toString),withLabelText(statusText)))
    }
  }



