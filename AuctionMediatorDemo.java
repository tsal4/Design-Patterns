/*
This program violates the Mediator Pattern with some issues in:

1. AuctionHouse Class
2. Bidder

Find (and tell what the issues are) then fix them. 

*/

import java.util.ArrayList;
import java.util.List;

// Mediator Interface
interface AuctionMediator {
    void registerBidder(Bidder bidder);
    void placeBid(Bidder bidder, double amount);
}

// Concrete Mediator (Auction House)
class AuctionHouse implements AuctionMediator {
    private List<Bidder> bidders = new ArrayList<>();
    private double highestBid = 0;
    private Bidder highestBidder;

    @Override
    public void registerBidder(Bidder bidder) {
        bidders.add(bidder);
    }

    @Override
    public void placeBid(Bidder bidder, double amount) {
        if (amount > highestBid) {
            highestBid = amount;
            highestBidder = bidder;
            notifyBidders();
        } else {
            System.out.println("[Auction] Bid rejected: $" + amount + " is too low.");
        }
    }

    private void notifyBidders() {
        for (Bidder bidder : bidders) {
            if (bidder != highestBidder) {
                bidder.receiveNotification("New highest bid: $" + highestBid);
            }
        }
    }

    public List<Bidder> getBidders() { 
        return bidders;
    }

    public void closeAuction() {
        if (highestBidder != null) {
            System.out.println("[Auction] Winner: " + highestBidder.getName() + " with bid $" + highestBid);
        } else {
            System.out.println("[Auction] No winner, no bids placed.");
        }
    }
}

// Bidder Class 
class Bidder {
    private String name;
    private AuctionMediator mediator;
    private List<Bidder> otherBidders; 

    public Bidder(AuctionMediator mediator, String name, List<Bidder> otherBidders) {
        this.mediator = mediator;
        this.name = name;
        this.otherBidders = otherBidders; 
        mediator.registerBidder(this);
    }

    public void placeBid(double amount) {
        System.out.println(name + " is placing a bid of $" + amount);
        mediator.placeBid(this, amount);
    }

    public void notifyOthers(String message) { 
        for (Bidder bidder : otherBidders) {
            bidder.receiveNotification(name + " says: " + message);
        }
    }

    public void receiveNotification(String message) {
        System.out.println(name + " received notification: " + message);
    }

    public String getName() {
        return name;
    }
}

// Main Class
public class AuctionMediatorDemo {
    public static void main(String[] args) {
        AuctionHouse auctionHouse = new AuctionHouse();

        
        Bidder alice = new Bidder(auctionHouse, "Alice", auctionHouse.getBidders());
        Bidder bob = new Bidder(auctionHouse, "Bob", auctionHouse.getBidders());
        Bidder charlie = new Bidder(auctionHouse, "Charlie", auctionHouse.getBidders());

        alice.placeBid(100);
        bob.placeBid(120);
        charlie.placeBid(150);

        
        alice.notifyOthers("I am out of the auction!");

        auctionHouse.closeAuction();
    }
}
