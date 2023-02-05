package com.yieldlab.biddingservice.service;

import com.yieldlab.biddingservice.dto.BidResponseDTO;

import java.util.Map;

public interface BidsService {

    BidResponseDTO holdAuction(Long adId, Map<String, String> params);

}
