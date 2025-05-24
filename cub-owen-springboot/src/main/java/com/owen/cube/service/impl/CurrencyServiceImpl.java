package com.owen.cube.service.impl;

import com.owen.coindesk.CoinDeskClient;
import com.owen.coindesk.dto.CoinDesk;
import com.owen.coindesk.dto.CurrencyInfo;
import com.owen.coindesk.mapper.CurrencyInfoMapper;
import com.owen.coindesk.model.CoinDeskResponse;
import com.owen.cube.cubclient.ClientCurrency;
import com.owen.cube.cubclient.CubClientRequest;
import com.owen.cube.cubclient.CubClientResponse;
import com.owen.cube.dto.CurrencyCreateRequest;
import com.owen.cube.dto.CurrencyUpdateRequest;
import com.owen.cube.entity.Currency;
import com.owen.cube.repository.CurrencyRepository;
import com.owen.cube.rowmapper.CurrencyMapper;
import com.owen.cube.service.CurrencyService;
import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {


    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;
    private final CubClientRequest cubClientRequest;
    private final CurrencyInfoMapper currencyInfoMapper;
    private final CoinDeskClient coindeskClient;

    @PostConstruct
    public void init() {
        log.info("CurrencyServiceImpl init");
      saveAllCurrencies();
    }


    public CurrencyServiceImpl(CurrencyRepository currencyRepository
                , CurrencyMapper currencyMapper
                , CubClientRequest cubClientRequest
                , CurrencyInfoMapper currencyInfoMapper
    , CoinDeskClient coindeskClient) {
        this.currencyRepository = currencyRepository;
        this.currencyMapper = currencyMapper;
        this.cubClientRequest = cubClientRequest;
        this.currencyInfoMapper = currencyInfoMapper;
        this.coindeskClient = coindeskClient;
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

    @Override
    public CoinDesk getCoinDeskData() {

        CoinDeskResponse coinDeskResponse = coindeskClient.getCoinDeskData();
        if (coinDeskResponse == null || coinDeskResponse.getTime() == null || coinDeskResponse.getBpi() == null) {
            throw new IllegalStateException("Coindesk respones is null or empty.");
        }

        log.info("CoinDesk API Response: {}", coinDeskResponse);

        String updateTime = formatUpdateTime(coinDeskResponse.getTime().getUpdatedISO());

        List<CurrencyInfo> currencyInfoList = coinDeskResponse.getBpi().values().stream()
                .map(currencyInfoMapper::toCurrencyInfo)
                .peek(currencyInfo ->
                        currencyRepository.findById(currencyInfo.getCode().toUpperCase())
                                .ifPresent(currencyRef -> currencyInfo.setNameZh(currencyRef.getNameZh()))
                )
                .collect(Collectors.toList());

        return CoinDesk.builder().updateTime(updateTime).currencyInfoList(currencyInfoList).build();
    }

    private String formatUpdateTime(LocalDateTime utcTime) {
        ZonedDateTime zonedUtc = utcTime.atZone(ZoneId.of("UTC"));
        return zonedUtc.withZoneSameInstant(ZoneId.of("Asia/Taipei"))
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
    private void currencySetNameZh(CurrencyInfo currencyInfo) {
        currencyRepository.findById(currencyInfo.getCode().toUpperCase())
                .ifPresent(currencyRef -> currencyInfo.setNameZh(currencyRef.getNameZh()));
    }
}
