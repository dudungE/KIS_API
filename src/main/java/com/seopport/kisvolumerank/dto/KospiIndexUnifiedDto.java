package com.seopport.kisvolumerank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KospiIndexUnifiedDto {
    // output2 전용
    @JsonProperty("stck_bsop_date")
    private String stckBsopDate;
    @JsonProperty("mod_yn")
    private String modYn;

    // output1 전용
    @JsonProperty("bstp_nmix_prdy_vrss")
    private String bstpNmixPrdyVrss;
    @JsonProperty("prdy_vrss_sign")
    private String prdyVrssSign;
    @JsonProperty("bstp_nmix_prdy_ctrt")
    private String bstpNmixPrdyCtrt;
    @JsonProperty("prdy_nmix")
    private String prdyNmix;
    @JsonProperty("hts_kor_isnm")
    private String htsKorIsnm;
    @JsonProperty("bstp_cls_code")
    private String bstpClsCode;
    @JsonProperty("prdy_vol")
    private String prdyVol;
    @JsonProperty("futs_prdy_oprc")
    private String futsPrdyOprc;
    @JsonProperty("futs_prdy_hgpr")
    private String futsPrdyHgpr;
    @JsonProperty("futs_prdy_lwpr")
    private String futsPrdyLwpr;

    // 공통 필드
    @JsonProperty("bstp_nmix_prpr")
    private String bstpNmixPrpr;
    @JsonProperty("bstp_nmix_oprc")
    private String bstpNmixOprc;
    @JsonProperty("bstp_nmix_hgpr")
    private String bstpNmixHgpr;
    @JsonProperty("bstp_nmix_lwpr")
    private String bstpNmixLwpr;
    @JsonProperty("acml_vol")
    private String acmlVol;
    @JsonProperty("acml_tr_pbmn")
    private String acmlTrPbmn;
}