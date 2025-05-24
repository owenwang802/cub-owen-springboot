package com.owen.cubowenspringboot.controller;


import com.owen.cubowenspringboot.dto.CurrencyCreateRequest;
import com.owen.cubowenspringboot.dto.CurrencyUpdateRequest;
import com.owen.cubowenspringboot.entity.Currency;
import com.owen.cubowenspringboot.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@Slf4j
@Validated
@RequestMapping("/api/v1/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }


    // 查詢
    @GetMapping("/{code}")
    public ResponseEntity<Currency> getCurrencyByCode(@PathVariable String code){
        Optional<Currency> currency = currencyService.getCurrencyByCode(code);

        return currency.map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }
    // 新增
    @PostMapping
   public ResponseEntity<?> createCurrency(@RequestBody @Valid CurrencyCreateRequest currencyCreateRequest){
        currencyService.createCurrency(currencyCreateRequest);
        return new ResponseEntity<>(currencyCreateRequest, HttpStatus.CREATED);
   }
    // 修改
    @PutMapping("/{code}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable @NotNull String code,
                                            @RequestBody @Valid CurrencyUpdateRequest currencyCreateRequest){

        String codeToUpperCase = code.trim().toUpperCase();

        Optional<Currency> currency = currencyService.getCurrencyByCode(codeToUpperCase);

        if (!currency.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Currency updated = currencyService.updateCurrency(codeToUpperCase, currencyCreateRequest);
        return ResponseEntity.ok(updated);

    }

    // 刪除
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable  String code){

        if (code == null || code.trim().isEmpty()) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }

        currencyService.deleteCurrencyByCode(code);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
