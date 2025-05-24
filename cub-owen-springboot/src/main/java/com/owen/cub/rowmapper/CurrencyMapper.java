package com.owen.cub.rowmapper;

import com.owen.cub.cubclient.ClientCurrency;
import com.owen.cub.dto.CurrencyCreateRequest;
import com.owen.cub.dto.CurrencyUpdateRequest;
import com.owen.cub.entity.Currency;
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
