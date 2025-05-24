package com.owen.cub.service;


import com.owen.coindesk.dto.CoinDesk;
import com.owen.cub.dto.CurrencyCreateRequest;
import com.owen.cub.dto.CurrencyUpdateRequest;
import com.owen.cub.entity.Currency;

import java.util.Optional;


public interface CurrencyService {

    Optional<Currency> getCurrencyByCode(String code);

    void saveAllCurrencies();

    void createCurrency(CurrencyCreateRequest currencyCreateRequest);

    void deleteCurrencyByCode(String code);

    Currency updateCurrency(String code, CurrencyUpdateRequest request);

    CoinDesk getCoinDeskData();
}
