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
public class PriceChangeRankDto {

    /** 종목 단축코드 (예: 005930) */
    @JsonProperty("stck_shrn_iscd")
    private String stck_shrn_iscd;

    /** 데이터 순위 (랭킹) */
    @JsonProperty("data_rank")
    private String data_rank;

    /** 한글 종목명 (예: 삼성전자) */
    @JsonProperty("hts_kor_isnm")
    private String hts_kor_isnm;

    /** 현재가 */
    @JsonProperty("stck_prpr")
    private String stck_prpr;

    /** 전일 대비 가격 변화 (숫자) */
    @JsonProperty("prdy_vrss")
    private String prdy_vrss;

    /** 전일 대비 등락 부호 (1: 하락, 2: 상승, 3: 보합) */
    @JsonProperty("prdy_vrss_sign")
    private String prdy_vrss_sign;

    /** 전일 대비 등락률(%) */
    @JsonProperty("prdy_ctrt")
    private String prdy_ctrt;

    /** 누적 거래량 */
    @JsonProperty("acml_vol")
    private String acml_vol;

    /** 당일 최고가 */
    @JsonProperty("stck_hgpr")
    private String stck_hgpr;

    /** 최고가 발생 시각 (HHmm) */
    @JsonProperty("hgpr_hour")
    private String hgpr_hour;

    /** 최고가 기록 일자 (YYYYMMDD) */
    @JsonProperty("acml_hgpr_date")
    private String acml_hgpr_date;

    /** 당일 최저가 */
    @JsonProperty("stck_lwpr")
    private String stck_lwpr;

    /** 최저가 발생 시각 (HHmm) */
    @JsonProperty("lwpr_hour")
    private String lwpr_hour;

    /** 최저가 기록 일자 (YYYYMMDD) */
    @JsonProperty("acml_lwpr_date")
    private String acml_lwpr_date;

    /** 최저가 대비 현재가 등락률(%) */
    @JsonProperty("lwpr_vrss_prpr_rate")
    private String lwpr_vrss_prpr_rate;

    /** 전일 종가 대비 현재가 등락률(%) */
    @JsonProperty("dsgt_date_clpr_vrss_prpr_rate")
    private String dsgt_date_clpr_vrss_prpr_rate;

    /** 연속 상승 일수 */
    @JsonProperty("cnnt_ascn_dynu")
    private String cnnt_ascn_dynu;

    /** 최고가 대비 현재가 등락률(%) */
    @JsonProperty("hgpr_vrss_prpr_rate")
    private String hgpr_vrss_prpr_rate;

    /** 연속 하락 일수 */
    @JsonProperty("cnnt_down_dynu")
    private String cnnt_down_dynu;

    /** 시가 대비 현재가 등락 부호 (1: 하락, 2: 상승, 3: 보합) */
    @JsonProperty("oprc_vrss_prpr_sign")
    private String oprc_vrss_prpr_sign;

    /** 시가 대비 현재가 등락액 */
    @JsonProperty("oprc_vrss_prpr")
    private String oprc_vrss_prpr;

    /** 시가 대비 현재가 등락률(%) */
    @JsonProperty("oprc_vrss_prpr_rate")
    private String oprc_vrss_prpr_rate;

    /** 기간 누적 상승/하락액 */
    @JsonProperty("prd_rsfl")
    private String prd_rsfl;

    /** 기간 누적 상승/하락률(%) */
    @JsonProperty("prd_rsfl_rate")
    private String prd_rsfl_rate;
}
