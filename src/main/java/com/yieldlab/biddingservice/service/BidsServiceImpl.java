package com.yieldlab.biddingservice.service;

import com.yieldlab.biddingservice.dto.BidRequestDTO;
import com.yieldlab.biddingservice.dto.BidResponseDTO;
import com.yieldlab.biddingservice.integration.bidders.BiddersGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BidsServiceImpl implements BidsService {

    private final BiddersGateway biddersGateway;

    @Override
    public BidResponseDTO holdAuction(Integer adId, Map<String, String> params) {

        BidRequestDTO bidRequest = new BidRequestDTO(adId, params);

        //TODO better return new class object instead of BidResponseDTO
        List<BidResponseDTO> bidResponses = biddersGateway.getBidsForAd(bidRequest);
        return chooseWinningBid(bidResponses);
    }

    private BidResponseDTO chooseWinningBid(List<BidResponseDTO> bidsList) {
        return bidsList.stream()
                .max(Comparator.comparingInt(BidResponseDTO::getBid)).orElseThrow();
        //TODO process NoSuchElementException (when list is empty)
    }
}
