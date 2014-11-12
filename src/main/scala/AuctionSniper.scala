
import java.util.EventListener

class AuctionSniper(auction: Auction, sniperListener: SniperListener) extends AuctionEventListener {

   def auctionClosed(){
    sniperListener.sniperLost()
  }

  override def currentPrice(price: Int, increment: Int) ={
    auction.bid(price + increment)
    sniperListener.sniperBidding()
  }


}

trait SniperListener extends EventListener {
  def sniperBidding()

  def sniperLost()

}



