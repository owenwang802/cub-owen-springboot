package com.owen.cube.cubclient;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CubClientRequest {

    @Value("${opencube.api.baseurl}")
    private String baseUrl;

    @Value("${opencube.api.uri.currency}")
    private String currencyUri;

    private final WebClient.Builder webClientBuilder;

    public CubClientRequest(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<CubClientResponse> getCurrencyAsync() {
        return webClientBuilder.baseUrl(baseUrl)
                .build()
                .get()
                .uri(currencyUri)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(CubClientResponse.class);
    }
}
