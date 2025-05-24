package com.owen.cube.service;


import com.owen.coindesk.dto.CoinDesk;
import com.owen.cube.dto.CurrencyCreateRequest;
import com.owen.cube.dto.CurrencyUpdateRequest;
import com.owen.cube.entity.Currency;

import java.util.Optional;


public interface CurrencyService {

    Optional<Currency> getCurrencyByCode(String code);

    void saveAllCurrencies();

    void createCurrency(CurrencyCreateRequest currencyCreateRequest);

    void deleteCurrencyByCode(String code);

    Currency updateCurrency(String code, CurrencyUpdateRequest request);

    CoinDesk getCoinDeskData();
}
