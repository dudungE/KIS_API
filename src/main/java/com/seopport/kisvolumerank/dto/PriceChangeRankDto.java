package com.seopport.kisvolumerank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class PriceChangeRankDto {
    @JsonProperty("stck_shrn_iscd")
    private String stockShortCode;

    @JsonProperty("data_rank")
    private String dataRank;

    @JsonProperty("hts_kor_isnm")
    private String koreanStockName;

    @JsonProperty("stck_prpr")
    private String currentPrice;

    @JsonProperty("prdy_vrss")
    private String priceChange;

    @JsonProperty("prdy_vrss_sign")
    private String priceChangeSign;

    @JsonProperty("prdy_ctrt")
    private String priceChangeRate;

    @JsonProperty("acml_vol")
    private String accumulatedVolume;

    @JsonProperty("stck_hgpr")
    private String highestPrice;

    @JsonProperty("hgpr_hour")
    private String highestPriceHour;

    @JsonProperty("acml_hgpr_date")
    private String highestPriceDate;

    @JsonProperty("stck_lwpr")
    private String lowestPrice;

    @JsonProperty("lwpr_hour")
    private String lowestPriceHour;

    @JsonProperty("acml_lwpr_date")
    private String lowestPriceDate;

    @JsonProperty("lwpr_vrss_prpr_rate")
    private String lowestPriceToCurrentRate;

    @JsonProperty("dsgt_date_clpr_vrss_prpr_rate")
    private String previousCloseToCurrentRate;

    @JsonProperty("cnnt_ascn_dynu")
    private String consecutiveUpDays;

    @JsonProperty("hgpr_vrss_prpr_rate")
    private String highestPriceChangeRate;

    @JsonProperty("cnnt_down_dynu")
    private String consecutiveDownDays;

    @JsonProperty("oprc_vrss_prpr_sign")
    private String openPriceSign;

    @JsonProperty("oprc_vrss_prpr")
    private String openPriceChange;

    @JsonProperty("oprc_vrss_prpr_rate")
    private String openPriceChangeRate;

    @JsonProperty("prd_rsfl")
    private String periodRiseFall;

    @JsonProperty("prd_rsfl_rate")
    private String periodRiseFallRate;


}
