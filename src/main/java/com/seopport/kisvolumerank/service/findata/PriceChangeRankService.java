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

                    dto.setStck_shrn_iscd(node.get("stck_shrn_iscd").asText());
                    dto.setData_rank(node.get("data_rank").asText());
                    dto.setHts_kor_isnm(node.get("hts_kor_isnm").asText());
                    dto.setStck_prpr(node.get("stck_prpr").asText());
                    dto.setPrdy_vrss(node.get("prdy_vrss").asText());
                    dto.setPrdy_vrss_sign(node.get("prdy_vrss_sign").asText());
                    dto.setPrdy_ctrt(node.get("prdy_ctrt").asText());
                    dto.setAcml_vol(node.get("acml_vol").asText());
                    dto.setStck_hgpr(node.get("stck_hgpr").asText());
                    dto.setHgpr_hour(node.get("hgpr_hour").asText());
                    dto.setAcml_hgpr_date(node.get("acml_hgpr_date").asText());
                    dto.setStck_lwpr(node.get("stck_lwpr").asText());
                    dto.setLwpr_hour(node.get("lwpr_hour").asText());
                    dto.setAcml_lwpr_date(node.get("acml_lwpr_date").asText());
                    dto.setLwpr_vrss_prpr_rate(node.get("lwpr_vrss_prpr_rate").asText());
                    dto.setDsgt_date_clpr_vrss_prpr_rate(node.get("dsgt_date_clpr_vrss_prpr_rate").asText());
                    dto.setCnnt_ascn_dynu(node.get("cnnt_ascn_dynu").asText());
                    dto.setHgpr_vrss_prpr_rate(node.get("hgpr_vrss_prpr_rate").asText());
                    dto.setCnnt_down_dynu(node.get("cnnt_down_dynu").asText());
                    dto.setOprc_vrss_prpr_sign(node.get("oprc_vrss_prpr_sign").asText());
                    dto.setOprc_vrss_prpr(node.get("oprc_vrss_prpr").asText());
                    dto.setOprc_vrss_prpr_rate(node.get("oprc_vrss_prpr_rate").asText());
                    dto.setPrd_rsfl(node.get("prd_rsfl").asText());
                    dto.setPrd_rsfl_rate(node.get("prd_rsfl_rate").asText());


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
}

