import com.objogate.wl.swing.AWTEventQueueProber
import com.objogate.wl.swing.driver.ComponentDriver._
import com.objogate.wl.swing.driver._
import com.objogate.wl.swing.gesture.GesturePerformer
import com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText
import com.objogate.wl.swing.matcher.IterableComponentsMatcher.matching
import javax.swing.table.JTableHeader

/**
 * Created by Uri_Keinan on 10/15/14.
 */
class AuctionSniperDriver(timeoutMillis: Int) extends JFrameDriver (
  new GesturePerformer(),
  JFrameDriver.topLevelFrame(named(Main.MAIN_WINDOW_NAME), showingOnScreen()),
  new AWTEventQueueProber(timeoutMillis, 100)){
  def hasColumnTitles() = {
    val headers = new JTableHeaderDriver(this, classOf[JTableHeader])
    headers.hasHeaders(matching(withLabelText("Item"), withLabelText("Last Price"), withLabelText("Last Bid"), withLabelText("State")))
  }


  def showsSniperStatus(itemId:String, lastPrice:Int, lastBid:Int, statusText: String){
      new JTableDriver(this).hasRow(matching(
        withLabelText(itemId),withLabelText(lastPrice.toString),
        withLabelText(lastBid.toString),withLabelText(statusText)))
    }
  }



