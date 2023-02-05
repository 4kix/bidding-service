package com.yieldlab.biddingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidResponseDTO {

    private Long id;
    private Integer bid;
    private String content;

}
