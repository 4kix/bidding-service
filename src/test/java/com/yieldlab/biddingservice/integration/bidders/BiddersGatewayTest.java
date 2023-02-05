package com.yieldlab.biddingservice.integration.bidders;

import com.yieldlab.biddingservice.dto.BidRequestDTO;
import com.yieldlab.biddingservice.dto.BidResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BiddersGatewayTest {

    private BiddersGateway biddersGateway;

    @Mock
    private BiddersConnector biddersConnector;

    @BeforeEach
    void setUp() {
        List<String> biddersUrlList = Arrays.asList("http://localhost:8081", "http://localhost:8082", "http://localhost:8083");
        biddersGateway = new BiddersGateway(biddersUrlList, biddersConnector);
    }

    @Test
    void getBidsForAd_whenBiddersAvailable_thenCollectResponses() {

        when(biddersConnector.postBidRequestToBidder(anyString(), any(BidRequestDTO.class)))
                .thenReturn(new ResponseEntity<>(new BidResponseDTO(1L, 150, "a:$price$"), HttpStatus.OK));

        List<BidResponseDTO> actualResult = biddersGateway.getBidsForAd(new BidRequestDTO());

        assertEquals(expectedResult(), actualResult);
    }

    private List<BidResponseDTO> expectedResult() {
        return Arrays.asList(
                new BidResponseDTO(1L, 150, "a:$price$"),
                new BidResponseDTO(1L, 150, "a:$price$"),
                new BidResponseDTO(1L, 150, "a:$price$"));
    }
}