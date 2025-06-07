package com.seopport.kisvolumerank.service.token;

import com.seopport.kisvolumerank.dto.token.TokenRequest;
import com.seopport.kisvolumerank.dto.token.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KisAuthService {

    private final WebClient webClient;

    @Value("${appkey}")
    private String appKey;

    @Value("${appsecret}")
    private String appSecret;

    @Value("${base_url}")
    private String tokenUrl;

    public KisAuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getAccessToken() {
        TokenRequest tokenRequest = new TokenRequest(appKey, appSecret);

        System.out.println(appKey + "debugjjh" + appSecret);

        TokenResponse response = webClient.post()
                .uri(tokenUrl+ "/oauth2/tokenP")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(tokenRequest)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block(); // 동기식으로 결과 받기

        System.out.println("debugjjh" + response);

        if (response != null && response.getAccessToken() != null) {
            return response.getAccessToken();
        } else {
            throw new RuntimeException("KIS 토큰 발급 실패 또는 응답 없음");
        }
    }
}



