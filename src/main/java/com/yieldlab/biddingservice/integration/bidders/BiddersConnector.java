package com.yieldlab.biddingservice.integration.bidders;

import com.yieldlab.biddingservice.dto.BidRequestDTO;
import com.yieldlab.biddingservice.dto.BidResponseDTO;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class BiddersConnector {

    private final RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(BiddersConnector.class);

    @Retry(name = "postBidderRetry")
    public ResponseEntity<BidResponseDTO> postBidRequestToBidder(String bidderUrl, BidRequestDTO bidRequest) {
        LOGGER.trace("Making a POST request to a bidder at {}", bidderUrl);
        return restTemplate.postForEntity(bidderUrl, bidRequest, BidResponseDTO.class);
    }

}
