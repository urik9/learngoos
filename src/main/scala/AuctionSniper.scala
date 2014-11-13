
import java.util.EventListener


class AuctionSniper(auction: Auction, sniperListener: SniperListener) extends AuctionEventListener {
  var isWinning = false


  def auctionClosed() {
    if (isWinning) {
      sniperListener.sniperWon()
    }
      else{
        sniperListener.sniperLost()
      }
    }


  override def currentPrice(price: Int, increment: Int, source: PriceSource.Value) = {
    source match {
      case PriceSource.FromSniper => sniperListener.sniperWinning(); isWinning = true
      case PriceSource.FromOtherBidder => {
        auction.bid(price + increment)
        sniperListener.sniperBidding()
      }
    }
  }
}

trait SniperListener extends EventListener {
  def sniperWon()

  def sniperWinning()

  def sniperBidding()

  def sniperLost()

}

trait Auction {
  def join()

  def bid(amount: Int)

}



