package com.seopport.kisvolumerank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KospiIndexOutput2Dto {
    @JsonProperty("stck_bsop_date")
    private String stckBsopDate;

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

    @JsonProperty("mod_yn")
    private String modYn;
}
