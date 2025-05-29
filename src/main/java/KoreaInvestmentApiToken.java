import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

public class KoreaInvestmentApiToken {

    public static void main(String[] args) throws Exception {
        String appKey = "";
        String appSecret = "";

        System.out.println("Try");
        KisApiConfig kisApiConfig = new KisApiConfig("application.properties");

        // 실전: https://openapi.koreainvestment.com:9443
        // 모의: https://openapivts.koreainvestment.com:29443
        String url = "https://openapivts.koreainvestment.com:29443/oauth2/tokenP";

        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();

        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "client_credentials");
        body.put("appkey", kisApiConfig.getAppKey());
        body.put("appsecret", kisApiConfig.getAppSecret());

        String accessToken = webClient.post()
                .uri("")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .map(TokenResponse::getAccess_token)
                .block();

        System.out.println("Access Token: " + accessToken);
    }

    // 응답 객체
    public static class TokenResponse {
        private String access_token;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }
    }
}



