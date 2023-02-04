package com.yieldlab.biddingservice.exception;

public class NoBidsForAuctionException extends RuntimeException{
    public NoBidsForAuctionException(String message) {
        super(message);
    }
}
