package com.owen.coindesk;

import com.owen.coindesk.model.CoinDeskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CoinDeskClient {

  @Value("${kengp3github.api.baseurl}")
  private String baseUrl;
  @Value("${kengp3github.api.uri.coindesk}")
  private String coindeskUri;

  private final WebClient.Builder webClientBuilder;

  public CoinDeskResponse getCoinDeskData() {
    return webClientBuilder.baseUrl(baseUrl)
            .build()
            .get()
            .uri(coindeskUri)
            .retrieve()
            .bodyToMono(CoinDeskResponse.class)
            .block();
  }
}
