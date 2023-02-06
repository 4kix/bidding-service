package com.yieldlab.biddingservice.controller;

import com.yieldlab.biddingservice.dto.BidResponseDTO;
import com.yieldlab.biddingservice.exception.BiddersExceptionHandler;
import com.yieldlab.biddingservice.exception.NoBidsForAuctionException;
import com.yieldlab.biddingservice.service.BidsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BiddingControllerTest {

    @InjectMocks
    private BiddingController biddingController;

    @Mock
    private BidsService bidsService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(biddingController)
                .setControllerAdvice(new BiddersExceptionHandler())
                .build();
    }

    @Test
    void initiateAuction_whenHasWinner_thenContent() throws Exception {
        when(bidsService.holdAuction(anyLong(), anyMap()))
                .thenReturn(new BidResponseDTO(1L, 250, "a:$price$"));

        MockHttpServletResponse response = mockMvc.perform(get("/1").queryParam("a","5"))
                .andReturn().getResponse();
        verify(bidsService, times(1)).holdAuction(anyLong(), anyMap());

        assertEquals(response.getContentAsString(), "a:250");
    }

    @Test
    void initiateAuction_whenNoBidsFound_thenNotFound() throws Exception {
        when(bidsService.holdAuction(anyLong(), anyMap()))
                .thenThrow(new NoBidsForAuctionException("No bids where found for the auction"));

        MockHttpServletResponse response = mockMvc.perform(get("/1").queryParam("a","5"))
                .andReturn().getResponse();

        assertTrue(response.getContentAsString().isEmpty());
        assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
    }
}