package com.yieldlab.biddingservice.controller;

import com.yieldlab.biddingservice.dto.BidResponseDTO;
import com.yieldlab.biddingservice.service.BidsService;
import com.yieldlab.biddingservice.util.BidContentFormatter;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("{id}")
    public ResponseEntity<String> initiateAuction(@PathVariable Integer id, @RequestParam Map<String, String> params) {
        BidResponseDTO auctionWinner = bidsService.holdAuction(id, params);
        String formattedResponse = BidContentFormatter.formatBidContent(auctionWinner);
        return ResponseEntity.ok(formattedResponse);
    }

}