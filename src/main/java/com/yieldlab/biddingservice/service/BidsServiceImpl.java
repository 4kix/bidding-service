package com.yieldlab.biddingservice.service;

import com.yieldlab.biddingservice.dto.BidRequestDTO;
import com.yieldlab.biddingservice.dto.BidResponseDTO;
import com.yieldlab.biddingservice.integration.bidders.BiddersGateway;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BidsServiceImpl implements BidsService {

    private final BiddersGateway biddersGateway;

    private static final Logger LOGGER = LoggerFactory.getLogger(BidsServiceImpl.class);

    @Override
    public BidResponseDTO holdAuction(Integer adId, Map<String, String> params) {

        BidRequestDTO bidRequest = new BidRequestDTO(adId, params);

        //TODO better return new class object instead of BidResponseDTO
        List<BidResponseDTO> bidResponses = biddersGateway.getBidsForAd(bidRequest);
        if (bidResponses.isEmpty()) {
            LOGGER.warn("No bids were found for the auction. Ad ID: {}, attributes: {}", adId, params);
        }
        return chooseWinningBid(bidResponses);
    }

    private BidResponseDTO chooseWinningBid(List<BidResponseDTO> bidsList) {
        return bidsList.stream()
                .max(Comparator.comparingInt(BidResponseDTO::getBid)).orElseThrow();
        //TODO process NoSuchElementException (when list is empty)
    }
}
