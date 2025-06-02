package com.seopport.kisvolumerank.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seopport.kisvolumerank.dto.KospiIndexDailyDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class KospiService {
    @Value("${kis.api.base-url}")
    private String baseUrl;

    @Value("${kis.api.appkey}")
    private String appKey;

    @Value("${kis.api.appsecret}")
    private String appSecret;

    @Value("${kis.api.token}")
    private String accessToken;

    private final ObjectMapper objectMapper = new ObjectMapper();



    //=======================================================================

    // 3. 코스피 일별 지수 데이터 조회 메서드
    public List<KospiIndexDailyDto> getKospiDailyIndices() {

        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        // 3-2. KIS API로 HTTP GET 요청 (WebClient 사용)
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
                // 3-3. 필수 헤더 설정
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header("appkey", appKey)
                .header("appsecret", appSecret)
                .header("tr_id", "FHKUP03500100")
                .header("custtype", "P")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("API RESPONSE:\n" + response);  // 정상작동

        try {
            JsonNode root = objectMapper.readTree(response);
            System.out.println("JsonModeDebug: " + root);
            List<KospiIndexDailyDto> result = new ArrayList<>();
            // 1. output1 처리 (단일 객체)
            JsonNode output1 = root.path("output1");

            if (output1.isObject() && !output1.isEmpty()) {
                KospiIndexDailyDto dto = objectMapper.treeToValue(output1, KospiIndexDailyDto.class);
                System.out.println("DTO Debug: " + dto); // 디버깅 출력
                result.add(dto);
            }

            System.out.println("resultDebug: " + result);

//            // 1. output1이 배열이면 사용
//            JsonNode output1 = root.path("output1");
//            System.out.println("output1Debug: " + output1);
//            if (output1.isArray() && !output1.isEmpty()) {
//                KospiIndexDailyDto dto = objectMapper.treeToValue(output1, KospiIndexDailyDto.class);
//                System.out.println("dtodebug" + dto);
//                result.add(dto);
//                for (JsonNode node : output1) {
//                    KospiIndexDailyDto dto = objectMapper.treeToValue(node, KospiIndexDailyDto.class);
//                    result.add(dto);
//                }
//            }


            // 2. output2도 배열이면 추가 (보통 기간 조회 시 사용)
//            JsonNode output2 = root.path("output2");
//            if (output2.isArray() && output2.size() > 0) {
//                for (JsonNode node : output2) {
//                    KospiIndexDailyDto dto = objectMapper.treeToValue(node, KospiIndexDailyDto.class);
//                    result.add(dto);
//                }
//            }

            // output2 파싱 시, 예외 발생해도 무시
            JsonNode output2 = root.path("output2");
            if (output2.isArray() && output2.size() > 0) {
                for (JsonNode node : output2) {
                    try {
                        KospiIndexDailyDto dto = objectMapper.treeToValue(node, KospiIndexDailyDto.class);
                        result.add(dto);
                    } catch (Exception e) {
                        System.out.println("output2 변환 실패: " + e.getMessage());
                        // 필요 시, output2에 맞는 별도 DTO로 파싱
                    }
                }
            }

            System.out.println("resultDebug: " + result);

            // 디버깅
            if (result.isEmpty()) {
                System.out.println("DEBUG: kospiList is empty!");
            } else {
                System.out.println("DEBUG: kospiList size = " + result.size());
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("KOSPI 데이터 파싱 실패", e);
        }
    }


}

