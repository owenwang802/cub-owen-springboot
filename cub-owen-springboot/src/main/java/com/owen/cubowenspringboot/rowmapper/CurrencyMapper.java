package com.owen.cubowenspringboot.rowmapper;

import com.owen.cubowenspringboot.cubclient.ClientCurrency;
import com.owen.cubowenspringboot.dto.CurrencyCreateRequest;
import com.owen.cubowenspringboot.dto.CurrencyUpdateRequest;
import com.owen.cubowenspringboot.entity.Currency;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {

    public Currency toCurrency(ClientCurrency currency) {
        return Currency.builder()
                .code(currency.getCurrency())
                .nameZh(currency.getNameTw())
                .nameEn(currency.getNameEn())
                .build();
    }

    public Currency toCurrency(CurrencyCreateRequest request) {
        return Currency.builder()
                .code(request.getCode().toUpperCase())
                .nameZh(request.getNameZh())
                .nameEn(request.getNameEn())
                .build();
    }
    public void updateCurrencyFromRequest(CurrencyUpdateRequest request, Currency currency) {
        currency.setNameZh(request.getNameZh());
        currency.setNameEn(request.getNameEn());

    }

}
