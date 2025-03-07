package com.currencyexchange.currencyexchangespringapplication;

import com.currencyexchange.currencyexchangespringapplication.dto.CurrencyCreationRequest;
import com.currencyexchange.currencyexchangespringapplication.dto.CurrencyCreationResponse;
import com.currencyexchange.currencyexchangespringapplication.entity.Currency;
import com.currencyexchange.currencyexchangespringapplication.repository.CurrencyRepository;
import com.currencyexchange.currencyexchangespringapplication.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceUnitTest {
    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    public void testAddCurrencySuccess() {
        CurrencyCreationRequest request = new CurrencyCreationRequest("USD", "US Dollar", "$");
        when(currencyRepository.findByCode("USD")).thenReturn(Optional.empty());

        Currency newCurrency = new Currency("USD", "US Dollar", "$");
        Currency savedCurrency = new Currency(1L, "USD", "US Dollar", "$");
        when(currencyRepository.save(newCurrency)).thenReturn(savedCurrency);

        CurrencyCreationResponse response = currencyService.addCurrency(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("USD", response.getCode());
        assertEquals("US Dollar", response.getFullName());
        assertEquals("$", response.getSign());

        verify(currencyRepository, times(1)).findByCode("USD");
        verify(currencyRepository, times(1)).save(newCurrency);
    }

    @Test
    public void testAddCurrencyAlreadyExists() {
        CurrencyCreationRequest request = new CurrencyCreationRequest("GBP", "Sterling", "£");

        Currency existingCurrency = new Currency("GBP", "Sterling", "£");
        when(currencyRepository.findByCode("GBP")).thenReturn(Optional.of(existingCurrency));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyService.addCurrency(request);
        });
        assertEquals("Currency with code GBP already exists", exception.getMessage());

        verify(currencyRepository, times(1)).findByCode("GBP");
        verify(currencyRepository, never()).save(any());
    }
}
