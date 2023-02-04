package com.yieldlab.biddingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BiddersExceptionHandler {

    @ExceptionHandler(NoBidsForAuctionException.class)
    @ResponseStatus(HttpStatus.OK)
    public void handleNoBidsForAuctionException() {
    }

}
