
import java.util.EventListener


class AuctionSniper(auction: Auction, sniperListener: SniperListener) extends AuctionEventListener {


   def auctionClosed(){
    sniperListener.sniperLost()
  }

  override def currentPrice(price: Int, increment: Int, source: PriceSource.Value) ={
    source match {
      case PriceSource.FromSniper => sniperListener.sniperWinning();
      case PriceSource.FromOtherBidder => {
        auction.bid(price + increment)
        sniperListener.sniperBidding()
      }
      }

    }



}

trait SniperListener extends EventListener {
  def sniperWinning()

  def sniperBidding()

  def sniperLost()

}

trait Auction {
  def join()

  def bid(amount: Int)

}



