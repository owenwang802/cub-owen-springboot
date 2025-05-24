package com.owen.cubowenspringboot.service.impl;

import com.owen.cubowenspringboot.cubclient.ClientCurrency;
import com.owen.cubowenspringboot.cubclient.CubClientRequest;
import com.owen.cubowenspringboot.cubclient.CubClientResponse;
import com.owen.cubowenspringboot.dto.CurrencyCreateRequest;
import com.owen.cubowenspringboot.dto.CurrencyUpdateRequest;
import com.owen.cubowenspringboot.entity.Currency;
import com.owen.cubowenspringboot.repository.CurrencyRepository;
import com.owen.cubowenspringboot.rowmapper.CurrencyMapper;
import com.owen.cubowenspringboot.service.CurrencyService;
import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {


    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;
    private final  CubClientRequest cubClientRequest;



    @PostConstruct
    public void init() {
        log.info("CurrencyServiceImpl init");
      saveAllCurrencies();
    }


    public CurrencyServiceImpl(CurrencyRepository currencyRepository
                , CurrencyMapper currencyMapper
                , CubClientRequest cubClientRequest) {
        this.currencyRepository = currencyRepository;
        this.currencyMapper = currencyMapper;
        this.cubClientRequest = cubClientRequest;
    }


    @Override
    public Optional<Currency> getCurrencyByCode(String code) {
        return currencyRepository.findById(code.toUpperCase());
    }

    @Override
    public void saveAllCurrencies() {

        CubClientResponse response = cubClientRequest.getCurrencyAsync().block();

        if (response == null) {
            log.warn("Failed to sync currencies: response is null.");
            return;
        }

        if (response.getStatus() != 200) {
            log.warn("Failed to sync currencies: unexpected response status [{}].", response.getStatus());
            return;
        }

        Map<String, ClientCurrency> currencyMap = response.getCurrencyMap();
        if (currencyMap == null || currencyMap.isEmpty()) {
            log.warn("Failed to sync currencies: currency map is empty.");
            return;
        }

        List<Currency> currencyRefList = currencyMap.values().stream()
                .map(currencyMapper::toCurrency)
                .collect(Collectors.toList());

        currencyRepository.saveAll(currencyRefList);
        log.info("Get All Currencies: {} ", currencyRefList.size());

    }

    @Override
    public void createCurrency(CurrencyCreateRequest currencyCreateRequest) {
        currencyRepository.save(currencyMapper.toCurrency(currencyCreateRequest));
    }

    @Override
    public void deleteCurrencyByCode(String code) {
        currencyRepository.deleteById(code);
    }

    @Override
    public Currency updateCurrency(String code, CurrencyUpdateRequest request) {

        Optional<Currency>  currencyOptional = getCurrencyByCode(code);
        Currency currency = currencyOptional.get();
        currencyMapper.updateCurrencyFromRequest(request, currency);
        return currencyRepository.save(currency);


    }

}
