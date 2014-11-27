import java.util.EventListener


class AuctionSniper(itemId: String, auction: Auction, sniperListener: SniperListener) extends AuctionEventListener {
  var snapshot = SniperSnapshot.joining(itemId)
  var isWinning = false




  def notifyChange() {
    sniperListener.sniperStateChanged(snapshot)
  }

  override def currentPrice(price: Int, increment: Int, source: PriceSource.Value) = {
    source match {
      case PriceSource.FromSniper => {
        snapshot = snapshot.winning(price)
        isWinning = true
      }
      case PriceSource.FromOtherBidder => {
        val bid = price + increment
        auction.bid(bid)
        snapshot = snapshot.bidding(price, bid)
      }
    }
    notifyChange()
  }

  def auctionClosed() {
    snapshot = snapshot.closed()
    notifyChange()
  }
}

trait SniperListener extends EventListener {
  def sniperStateChanged(snapshot: SniperSnapshot)

}

trait Auction {
  def join()

  def bid(amount: Int)

}



