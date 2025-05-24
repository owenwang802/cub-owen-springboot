package com.owen.cub.cubclient;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CubClientResponse {
    @JsonProperty("data")
    private Map<String, ClientCurrency> CurrencyMap;
    @JsonProperty("status")
    private Integer status;
}
