package com.owen.cubowenspringboot.service;


import com.owen.cubowenspringboot.dto.CurrencyCreateRequest;
import com.owen.cubowenspringboot.dto.CurrencyUpdateRequest;
import com.owen.cubowenspringboot.entity.Currency;

import java.util.Optional;


public interface CurrencyService {

    Optional<Currency> getCurrencyByCode(String code);

    void saveAllCurrencies();

    void createCurrency(CurrencyCreateRequest currencyCreateRequest);

    void deleteCurrencyByCode(String code);

    Currency updateCurrency(String code, CurrencyUpdateRequest request);
}
