package com.yieldlab.biddingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class BidRequestDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("attributes")
    private Map<String,String> attributes;

}
