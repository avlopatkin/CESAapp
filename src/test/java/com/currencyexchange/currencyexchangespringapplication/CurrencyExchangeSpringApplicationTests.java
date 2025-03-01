package com.currencyexchange.currencyexchangespringapplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@Sql("/sql/currencies.sql")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class CurrencyExchangeSpringApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Rollback
    void shouldGetAllCurrencies() throws Exception {
        // given
        var requestBuilder = get("/api/currencies");

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.length()").value(2),
                        jsonPath("$[0].code").value("USD"),
                        jsonPath("$[0].name").value("US Dollar"),
                        jsonPath("$[0].sign").value("$"),
                        jsonPath("$[1].code").value("EUR"),
                        jsonPath("$[1].name").value("Euro"),
                        jsonPath("$[1].sign").value("€")
                );
    }

    @Test
    @Rollback
    void shouldCreateCurrency() throws Exception {
        // given
        var newCurrencyJson = """
            {
              "code": "GBP",
              "name": "British Pound",
              "sign": "£"
            }
            """;
        var requestBuilder = post("/api/currencies")
                .contentType("application/json")
                .content(newCurrencyJson);

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.code").value("GBP"),
                        jsonPath("$.name").value("British Pound"),
                        jsonPath("$.sign").value("£")
                );
    }

    @Test
    @Rollback
    void shouldGetCurrencyById() throws Exception {
        // given
        var requestBuilder = get("/api/currencies/{id}", 1);

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.code").value("USD"),
                        jsonPath("$.name").value("US Dollar"),
                        jsonPath("$.sign").value("$")
                );
    }

    @Test
    @Rollback
    void shouldUpdateCurrency() throws Exception {
        // given
        var updatedCurrencyJson = """
            {
              "name": "Updated Dollar",
              "sign": "$"
            }
            """;
        var requestBuilder = put("/api/currencies/{id}", 1)
                .contentType("application/json")
                .content(updatedCurrencyJson);

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.name").value("Updated Dollar"),
                        jsonPath("$.sign").value("$")
                );
    }

    @Test
    @Rollback
    void shouldDeleteCurrency() throws Exception {
        // given
        var requestBuilder = delete("/api/currencies/{id}", 1);

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/currencies/{id}", 1))
                .andExpect(status().isNotFound());
    }
}