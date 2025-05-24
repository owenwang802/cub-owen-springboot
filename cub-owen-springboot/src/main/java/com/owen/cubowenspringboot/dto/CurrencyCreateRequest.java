package com.owen.cubowenspringboot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class CurrencyCreateRequest {
    @NotBlank
    private String code;
    @NotBlank
    private String nameZh;
    @NotBlank
    private String nameEn;

}
