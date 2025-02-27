package com.currencyexchange.currencyexchangespringapplication;


class CurrencyIT {


//    @Test
//    void createCurrency_validInput_returnsCreatedCurrency() {
//        //Given
//        CurrencyCreationRequest request = new CurrencyCreationRequest("EUR", "Euro", "€");
//
//        //When
//        ResponseEntity<CurrencyDto> response = createCurrency(request);
//
//        //Then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(response.getBody())
//                .isNotNull()
//                .extracting(CurrencyDto::getCode, CurrencyDto::getFullName, CurrencyDto::getSign)
//                .containsExactly("EUR", "Euro", "€");
//    }
//
//    @Test
//    void getCurrency_existingCurrency_returnsCurrency() {
//        //Given
//        String code = "USD";
//        createCurrency(new CurrencyCreationRequest(code, "US Dollar", "USD"));
//
//        //When
//        ResponseEntity<CurrencyDto> response = getCurrency(code);
//
//        //Then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody())
//                .isNotNull()
//                .extracting(CurrencyDto::getCode)
//                .isEqualTo(code);
//    }
//
//    @Test
//    void updateCurrency_existingCurrency_returnsUpdatedCurrency() {
//        //Given
//        String code = "CAD";
//        createCurrency(new CurrencyCreationRequest(code, "Canadian Dollar", "CAD"));
//        CurrencyUpdateRequest updateRequest = new CurrencyUpdateRequest("Updated Canadian Dollar", "CAD");
//
//        //When
//        ResponseEntity<CurrencyDto> response = updateCurrency(code, updateRequest);
//
//        //Then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody())
//                .isNotNull()
//                .extracting(CurrencyDto::getFullName, CurrencyDto::getSign)
//                .containsExactly("Updated Canadian Dollar", "CAD");
//    }
//
//    @Test
//    void deleteCurrency_existingCurrency_returnsDeletedCurrency() {
//        //Given
//        String code = "AUD";
//        createCurrency(new CurrencyCreationRequest(code, "Australian Dollar", "AUD"));
//
//        //When
//        deleteCurrency(code);
//
//        //Then
//        ResponseEntity<CurrencyDto> response = getCurrency(code);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    void createCurrency_duplicateCurrency_returnsBadRequest() {
//        //Given
//        String code = "JPY";
//        createCurrency(new CurrencyCreationRequest(code, "Japanese Yen", "¥"));
//
//        //When
//        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(baseUrl(), new CurrencyCreationRequest(code,
//                "Duplicate Yen", "¥"), ErrorResponse.class);
//
//        //Then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(response.getBody())
//                .isNotNull()
//                .extracting(ErrorResponse::getTitleMessageCode)
//                .isEqualTo("Currency with code " + code + " already exists");
//    }
}
