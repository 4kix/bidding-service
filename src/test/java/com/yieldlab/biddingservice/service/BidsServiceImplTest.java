package com.yieldlab.biddingservice.service;

import com.yieldlab.biddingservice.dto.BidResponseDTO;
import com.yieldlab.biddingservice.exception.NoBidsForAuctionException;
import com.yieldlab.biddingservice.integration.bidders.BiddersGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BidsServiceImplTest {

    @InjectMocks
    private BidsServiceImpl bidsService;

    @Mock
    private BiddersGateway biddersGateway;

    private List<BidResponseDTO> bidResponses;
    private BidResponseDTO expectedWinner;

    @BeforeEach
    void setUp() {
        bidResponses = Arrays.asList(new BidResponseDTO(1L, 150, "a:$price$"),
                new BidResponseDTO(1L, 250, "b:$price$"));
        expectedWinner = new BidResponseDTO(1L, 250, "b:$price$");
    }

    @Test
    void holdAuction_whenAuctionSucceed_thenReturnWinner() {

        when(biddersGateway.getBidsForAd(anyLong(), anyMap()))
                .thenReturn(bidResponses);

        BidResponseDTO actualResult = bidsService.holdAuction(1L, new HashMap<>());
        assertEquals(actualResult, expectedWinner);
    }
    @Test
    void holdAuction_whenNoBidders_thenThrowsException() {
        when(biddersGateway.getBidsForAd(anyLong(), anyMap()))
                .thenReturn(Collections.emptyList());

        assertThrows(NoBidsForAuctionException.class, () -> bidsService.holdAuction(1L, new HashMap<>()));
    }

}