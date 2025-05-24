package com.owen.coindesk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinDesk {
    private String updateTime; // 格式：yyyy/MM/dd HH:mm:ss
    private List<CurrencyInfo> currencyInfoList;
}
