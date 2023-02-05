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

    //TODO make long or int?
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("attributes")
    private Map<String,String> attributes;

}
