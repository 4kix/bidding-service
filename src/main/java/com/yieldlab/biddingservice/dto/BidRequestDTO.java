package com.yieldlab.biddingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidRequestDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("attributes")
    private Map<String,String> attributes;

}
