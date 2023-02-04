package com.yieldlab.biddingservice.integration.bidders;

import com.yieldlab.biddingservice.dto.BidRequestDTO;
import com.yieldlab.biddingservice.dto.BidResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class BiddersGateway {

    @Value("#{'${bidders.urls}'.split(',')}")
    private List<String> biddersUrlList;

    private final RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(BidRequestDTO.class);

    public List<BidResponseDTO> getBidsForAd(BidRequestDTO bidRequest) {

        List<CompletableFuture<ResponseEntity<BidResponseDTO>>> bidResponseFuturesList = new ArrayList<>();
        biddersUrlList.forEach(url -> {
            CompletableFuture<ResponseEntity<BidResponseDTO>> bidResponseFuture =
                    CompletableFuture.supplyAsync(() -> postBidRequestToBidder(url.trim(), bidRequest))
                            .exceptionally(e -> {
                                LOGGER.error("Request to bidder at {} failed with the message: {}" , url, e.getMessage(), e);
                                return null;
                            });
            bidResponseFuturesList.add(bidResponseFuture);
        });

        return bidResponseFuturesList.stream().map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .map(ResponseEntity::getBody).toList();
    }

    private ResponseEntity<BidResponseDTO> postBidRequestToBidder(String bidderUrl, BidRequestDTO bidRequest) {
        LOGGER.trace("Making a POST request to a bidder at {}", bidderUrl);
        return restTemplate.postForEntity(bidderUrl.trim(), bidRequest, BidResponseDTO.class);
    }

}
