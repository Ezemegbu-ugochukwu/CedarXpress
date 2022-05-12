package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.request.FundWalletRequest;
import com.example.cedarxpressliveprojectjava010.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {WalletController.class})
@ExtendWith(SpringExtension.class)
class WalletControllerTest {

    @Autowired
    private WalletController walletController;

    @MockBean
    private WalletService walletService;

    @MockBean
    private FundWalletRequest fundWalletRequest;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void shouldBeAbleToFundWallet() throws Exception {

        fundWalletRequest = FundWalletRequest.builder()
                .email("chinekeebube@gmail.com")
                .amount(100.00)
                .build();

        when(walletService.fundWallet(fundWalletRequest)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        String content = (new ObjectMapper()).writeValueAsString(fundWalletRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/fund-wallet")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);


        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.walletController)
            .build()
            .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));

    }

}