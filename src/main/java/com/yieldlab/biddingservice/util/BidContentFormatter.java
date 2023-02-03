package com.yieldlab.biddingservice.util;

import com.yieldlab.biddingservice.dto.BidResponseDTO;

public class BidContentFormatter {

    public static String formatBidContent(BidResponseDTO bidResponseDTO) {
        String contentTemplate = bidResponseDTO.getContent();
        return contentTemplate.replace("$price$", bidResponseDTO.getBid().toString());
    }

}
