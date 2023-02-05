package com.yieldlab.biddingservice.integration.bidders;

import com.yieldlab.biddingservice.dto.BidRequestDTO;
import com.yieldlab.biddingservice.dto.BidResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class BiddersGateway {

    @Value("#{'${bidders.urls}'.split(',')}")
    private final List<String> biddersUrlList;

    private final BiddersConnector biddersConnector;

    private static final Logger LOGGER = LoggerFactory.getLogger(BidRequestDTO.class);

    public List<BidResponseDTO> getBidsForAd(Long adId, Map<String, String> params) {

        BidRequestDTO bidRequest = new BidRequestDTO(adId, params);

        List<CompletableFuture<ResponseEntity<BidResponseDTO>>> bidResponseFuturesList = new ArrayList<>();
        biddersUrlList.forEach(url -> {
            CompletableFuture<ResponseEntity<BidResponseDTO>> bidResponseFuture =
                    CompletableFuture.supplyAsync(() -> biddersConnector.postBidRequestToBidder(url.trim(), bidRequest))
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

}
