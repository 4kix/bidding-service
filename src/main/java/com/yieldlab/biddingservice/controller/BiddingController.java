package com.yieldlab.biddingservice.controller;

import com.yieldlab.biddingservice.dto.BidResponseDTO;
import com.yieldlab.biddingservice.service.BidsService;
import com.yieldlab.biddingservice.util.BidContentFormatter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BiddingController {

    private final BidsService bidsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BiddingController.class);

    @GetMapping("{id}")
    public ResponseEntity<String> initiateAuction(@PathVariable Integer id, @RequestParam Map<String, String> params) {
        LOGGER.info("Initiating a new auction for ad ID {} with params: {}", id, params.toString());
        BidResponseDTO auctionWinner = bidsService.holdAuction(id, params);
        String formattedResponse = BidContentFormatter.formatBidContent(auctionWinner);
        return ResponseEntity.ok(formattedResponse);
    }

}