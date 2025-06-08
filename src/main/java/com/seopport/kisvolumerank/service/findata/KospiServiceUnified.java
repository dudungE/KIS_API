package com.seopport.kisvolumerank.service.findata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seopport.kisvolumerank.dto.findata.KospiIndexUnifiedDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class KospiServiceUnified {
    @Value("${kis.api.base-url}")
    private String baseUrl;

    @Value("${kis.api.appkey}")
    private String appKey;

    @Value("${kis.api.appsecret}")
    private String appSecret;

    @Value("${kis.api.token}")
    private String accessToken;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<KospiIndexUnifiedDto> getKospiDailyIndices() {
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        String response = WebClient.builder()
                .baseUrl(baseUrl)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/uapi/domestic-stock/v1/quotations/inquire-daily-indexchartprice")
                        .queryParam("FID_COND_MRKT_DIV_CODE", "U")
                        .queryParam("FID_INPUT_ISCD", "0001")
                        .queryParam("FID_INPUT_DATE_1", today)
                        .queryParam("FID_INPUT_DATE_2", today)
                        .queryParam("FID_PERIOD_DIV_CODE", "D")
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header("appkey", appKey)
                .header("appsecret", appSecret)
                .header("tr_id", "FHKUP03500100")
                .header("custtype", "P")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("API RESPONSE:\n" + response);

        try {
            JsonNode root = objectMapper.readTree(response);
            List<KospiIndexUnifiedDto> result = new ArrayList<>();

            // output1 처리
            JsonNode output1 = root.path("output1");
            if (output1.isObject() && !output1.isEmpty()) {
                KospiIndexUnifiedDto dto = objectMapper.treeToValue(output1, KospiIndexUnifiedDto.class);
                result.add(dto);
            }

            // output2 처리
            JsonNode output2 = root.path("output2");
            if (output2.isArray()) {
                for (JsonNode node : output2) {
                    KospiIndexUnifiedDto dto = objectMapper.treeToValue(node, KospiIndexUnifiedDto.class);
                    result.add(dto);
                }
            }

            // 디버깅 로그
            if (result.isEmpty()) {
                System.out.println("DEBUG: kospiList is empty!");
            } else {
                System.out.println("DEBUG: kospiList size = " + result.size());
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("KOSPI 데이터 처리 실패", e);
        }
    }
}