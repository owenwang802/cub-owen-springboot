package com.owen.cube.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CurrencyUpdateRequest {

    @NotBlank
    private String nameZh;
    @NotBlank
    private String nameEn;



}
