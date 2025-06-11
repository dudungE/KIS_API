
//package com.seopport.kisvolumerank.dto.findata;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Data;
//
//
//@Data
//public class PriceChangeRankDto {
//    @JsonProperty("stck_shrn_iscd")
//    private String stockShortCode;
//
//    @JsonProperty("data_rank")
//    private String dataRank;
//
//    @JsonProperty("hts_kor_isnm")
//    private String koreanStockName;
//
//    @JsonProperty("stck_prpr")
//    private String currentPrice;
//
//    @JsonProperty("prdy_vrss")
//    private String priceChange;
//
//    @JsonProperty("prdy_vrss_sign")
//    private String priceChangeSign;
//
//    @JsonProperty("prdy_ctrt")
//    private String priceChangeRate;
//
//    @JsonProperty("acml_vol")
//    private String accumulatedVolume;
//
//    @JsonProperty("stck_hgpr")
//    private String highestPrice;
//
//    @JsonProperty("hgpr_hour")
//    private String highestPriceHour;
//
//    @JsonProperty("acml_hgpr_date")
//    private String highestPriceDate;
//
//    @JsonProperty("stck_lwpr")
//    private String lowestPrice;
//
//    @JsonProperty("lwpr_hour")
//    private String lowestPriceHour;
//
//    @JsonProperty("acml_lwpr_date")
//    private String lowestPriceDate;
//
//    @JsonProperty("lwpr_vrss_prpr_rate")
//    private String lowestPriceToCurrentRate;
//
//    @JsonProperty("dsgt_date_clpr_vrss_prpr_rate")
//    private String previousCloseToCurrentRate;
//
//    @JsonProperty("cnnt_ascn_dynu")
//    private String consecutiveUpDays;
//
//    @JsonProperty("hgpr_vrss_prpr_rate")
//    private String highestPriceChangeRate;
//
//    @JsonProperty("cnnt_down_dynu")
//    private String consecutiveDownDays;
//
//    @JsonProperty("oprc_vrss_prpr_sign")
//    private String openPriceSign;
//
//    @JsonProperty("oprc_vrss_prpr")
//    private String openPriceChange;
//
//    @JsonProperty("oprc_vrss_prpr_rate")
//    private String openPriceChangeRate;
//
//    @JsonProperty("prd_rsfl")
//    private String periodRiseFall;
//
//    @JsonProperty("prd_rsfl_rate")
//    private String periodRiseFallRate;
//
//
//}


package com.seopport.kisvolumerank.dto.findata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * 가격 변동 랭킹 DTO (필드명과 JSON 속성명을 동일하게 맞춤)
 */
@Data
public class PriceUpRankDto {

    @JsonProperty("code")
    private String code;           // 종목 코드

    @JsonProperty("name")
    private String name;           // 종목명

    @JsonProperty("daebi")
    private String daebi;          // 전일 대비 상태 (1:상승, 2:하락, 5:보합)

    @JsonProperty("price")
    private String price;          // 현재가

    @JsonProperty("chgrate")
    private String chgrate;        // 전일 대비 등락률 (%)

    @JsonProperty("acml_vol")
    private String acmlVol;        // 누적 거래량

    @JsonProperty("trade_amt")
    private String tradeAmt;       // 거래대금

    @JsonProperty("change")
    private String change;         // 전일 대비 가격 변화

    @JsonProperty("cttr")
    private String cttr;           // 체결 강도

    @JsonProperty("open")
    private String open;           // 시가

    @JsonProperty("high")
    private String high;           // 고가

    @JsonProperty("low")
    private String low;            // 저가

    @JsonProperty("high52")
    private String high52;         // 52주 최고가

    @JsonProperty("low52")
    private String low52;          // 52주 최저가

    @JsonProperty("expprice")
    private String expPrice;       // 예상 체결가

    @JsonProperty("expchange")
    private String expChange;      // 예상 체결가 대비 변화

    @JsonProperty("expchggrate")
    private String expChgGrate;    // 예상 체결가 대비 등락률

    @JsonProperty("expcvol")
    private String expCVol;        // 예상 체결량

    @JsonProperty("chgrate2")
    private String chgRate2;       // 기준 대비 등락률

    @JsonProperty("expdaebi")
    private String expDaebi;       // 예상 전일 대비 상태

    @JsonProperty("recprice")
    private String recPrice;       // 추천가

    @JsonProperty("uplmtprice")
    private String uplmtPrice;     // 상한가

    @JsonProperty("dnlmtprice")
    private String dnlmtPrice;     // 하한가

    @JsonProperty("stotprice")
    private String stotPrice;      // 총 거래대금

}
