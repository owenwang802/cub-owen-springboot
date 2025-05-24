package com.owen.coindesk.mapper;

import com.owen.coindesk.dto.CurrencyInfo;
import com.owen.coindesk.model.Bpi;
import org.springframework.stereotype.Component;

@Component
public class CurrencyInfoMapper {

    public CurrencyInfo toCurrencyInfo(Bpi bpi){

        return CurrencyInfo.builder()
                .code(bpi.getCode())
                .rate(bpi.getRate())
                .build();
    }


}
