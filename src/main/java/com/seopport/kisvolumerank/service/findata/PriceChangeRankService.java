package com.seopport.kisvolumerank.service.findata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seopport.kisvolumerank.dto.findata.PriceChangeRankDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceChangeRankService {
    @Value("${kis.api.appkey}")
    private String appkey;

    @Value("${kis.api.appsecret}")
    private String appSecret;

    @Value("${kis.api.token}")
    private String accessToken;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public PriceChangeRankService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("https://openapi.koreainvestment.com:9443").build();
        this.objectMapper = objectMapper;
    }

    private HttpHeaders createPriceChangeRankHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        headers.set("appkey", appkey);
        headers.set("appSecret", appSecret);
        headers.set("tr_id", "FHPST01700000");
        headers.set("custtype", "P");
        return headers;
    }

    // API 응답 파싱 (parsePriceChangeRank)
    private Mono<List<PriceChangeRankDto>> parsePriceChangeRank(String response) {
        try {
            List<PriceChangeRankDto> responseDataList = new ArrayList<>();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode outputNode = rootNode.get("output");
            if (outputNode != null) {
                for (JsonNode node : outputNode) {
                    PriceChangeRankDto dto = new PriceChangeRankDto();
                    dto.setStockShortCode(node.get("stck_shrn_iscd").asText());
                    dto.setDataRank(node.get("data_rank").asText());
                    dto.setKoreanStockName(node.get("hts_kor_isnm").asText());
                    dto.setCurrentPrice(node.get("stck_prpr").asText());
                    dto.setPriceChange(node.get("prdy_vrss").asText());
                    dto.setPriceChangeSign(node.get("prdy_vrss_sign").asText());
                    dto.setPriceChangeRate(node.get("prdy_ctrt").asText());
                    dto.setAccumulatedVolume(node.get("acml_vol").asText());
                    dto.setHighestPrice(node.get("stck_hgpr").asText());
                    dto.setHighestPriceHour(node.get("hgpr_hour").asText());
                    dto.setHighestPriceDate(node.get("acml_hgpr_date").asText());
                    dto.setLowestPrice(node.get("stck_lwpr").asText());
                    dto.setLowestPriceHour(node.get("lwpr_hour").asText());
                    dto.setLowestPriceDate(node.get("acml_lwpr_date").asText());
                    dto.setLowestPriceToCurrentRate(node.get("lwpr_vrss_prpr_rate").asText());
                    dto.setPreviousCloseToCurrentRate(node.get("dsgt_date_clpr_vrss_prpr_rate").asText());
                    dto.setConsecutiveUpDays(node.get("cnnt_ascn_dynu").asText());
                    dto.setHighestPriceChangeRate(node.get("hgpr_vrss_prpr_rate").asText());
                    dto.setConsecutiveDownDays(node.get("cnnt_down_dynu").asText());
                    dto.setOpenPriceSign(node.get("oprc_vrss_prpr_sign").asText());
                    dto.setOpenPriceChange(node.get("oprc_vrss_prpr").asText());
                    dto.setOpenPriceChangeRate(node.get("oprc_vrss_prpr_rate").asText());
                    dto.setPeriodRiseFall(node.get("prd_rsfl").asText());
                    dto.setPeriodRiseFallRate(node.get("prd_rsfl_rate").asText());

                    responseDataList.add(dto);
                }
            }
            return Mono.just(responseDataList);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    public Mono<List<PriceChangeRankDto>> getPriceChangeRank() {
        HttpHeaders headers = createPriceChangeRankHttpHeaders();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/uapi/domestic-stock/v1/ranking/fluctuation")
                        .queryParam("fid_cond_mrkt_div_code", "J")
                        .queryParam("fid_cond_scr_div_code", "20170")
                        .queryParam("fid_input_iscd", "0000")
                        .queryParam("fid_rank_sort_cls_code", "0")
                        .queryParam("fid_input_cnt_1", "0")
                        .queryParam("fid_prc_cls_code", "0")
                        .queryParam("fid_input_price_1", "")
                        .queryParam("fid_input_price_2", "")
                        .queryParam("fid_vol_cnt", "")
                        .queryParam("fid_trgt_cls_code", "0")
                        .queryParam("fid_trgt_exls_cls_code", "0")
                        .queryParam("fid_div_cls_code", "0")
                        .queryParam("fid_rsfl_rate1", "")
                        .queryParam("fid_rsfl_rate2", "")
                        .build())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> parsePriceChangeRank(response));
    }


//    public Mono<List<ResponseOutputDTO>> getVolumeRank() {
//        HttpHeaders headers = createPriceChangeRankHttpHeaders();
//
//        return webClient.get()
//                .uri(uriBuilder -> uriBuilder.path("/uapi/domestic-stock/v1/quotations/volume-rank")
//                        .queryParam("FID_COND_MRKT_DIV_CODE", "J")
//                        .queryParam("FID_COND_SCR_DIV_CODE", "20171")
//                        .queryParam("FID_INPUT_ISCD", "0002")
//                        .queryParam("FID_DIV_CLS_CODE", "0")
//                        .queryParam("FID_BLNG_CLS_CODE", "0")
//                        .queryParam("FID_TRGT_CLS_CODE", "111111111")
//                        .queryParam("FID_TRGT_EXLS_CLS_CODE", "000000")
//                        .queryParam("FID_INPUT_PRICE_1", "0")
//                        .queryParam("FID_INPUT_PRICE_2", "0")
//                        .queryParam("FID_VOL_CNT", "0")
//                        .queryParam("FID_INPUT_DATE_1", "0")
//                        .build())
//                .headers(httpHeaders -> httpHeaders.addAll(headers))
//                .retrieve()
//                .bodyToMono(String.class)
//                .flatMap(response -> parseFVolumeRank(response));
//    }


}

