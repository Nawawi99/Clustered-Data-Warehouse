package dev.awn.datawarehouse.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.awn.datawarehouse.core.deal.document.Deal;
import dev.awn.datawarehouse.core.deal.dto.DealDTO;
import dev.awn.datawarehouse.core.deal.mapper.DealMapper;
import dev.awn.datawarehouse.core.deal.repository.DealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DealControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                 .build();
    }

    @Test
    void testPersistDeal_HappyScenario() throws Exception {
        // Mock dependencies
        DealRepository dealRepository = Mockito.mock(DealRepository.class);
        DealMapper dealMapper = Mockito.mock(DealMapper.class);

        // Prepare input DealDTO
        DealDTO dealDTO = DealDTO.builder()
                                 .fromCurrencyISO("USD")
                                 .toCurrencyISO("EUR")
                                 .timestamp(LocalDateTime.now())
                                 .amount(1000.0)
                                 .build();

        // Mock repository and mapper behavior
        Deal savedDeal = Deal.builder()
                             .id("generatedId")
                             .fromCurrencyISO("USD")
                             .toCurrencyISO("EUR")
                             .timestamp(LocalDateTime.now())
                             .amount(1000.0)
                             .build();
        Mockito.when(dealRepository.save(Mockito.any(Deal.class)))
               .thenReturn(savedDeal);
        Mockito.when(dealMapper.toDTO(savedDeal))
               .thenReturn(dealDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/api/v1/deals")
                                                                             .contentType(MediaType.APPLICATION_JSON)
                                                                             .accept(MediaType.APPLICATION_JSON)
                                                                             .content(objectMapper.writeValueAsString(dealDTO));

        MvcResult result = mockMvc.perform(requestBuilder)
                                  .andReturn();

        // Assert response
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testPersistDeal_DuplicateDealException() throws Exception {
        DealRepository dealRepository = Mockito.mock(DealRepository.class);

        DealDTO dealDTO = DealDTO.builder()
                                 .id("existingId")
                                 .build();

        Mockito.when(dealRepository.existsById(Mockito.anyString()))
               .thenReturn(true);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/api/v1/deals")
                                                                             .contentType(MediaType.APPLICATION_JSON)
                                                                             .accept(MediaType.APPLICATION_JSON)
                                                                             .content(objectMapper.writeValueAsString(dealDTO));

        MvcResult result = mockMvc.perform(requestBuilder)
                                  .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse()
                                                           .getStatus());
    }

    @Test
    void testPersistDeal_MissingBodyFieldsException() throws Exception {
        DealDTO dealDTO = DealDTO.builder()
                                 .build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/api/v1/deals")
                                                                             .contentType(MediaType.APPLICATION_JSON)
                                                                             .accept(MediaType.APPLICATION_JSON)
                                                                             .content(objectMapper.writeValueAsString(dealDTO));

        MvcResult result = mockMvc.perform(requestBuilder)
                                  .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse()
                                                           .getStatus());
    }
}

