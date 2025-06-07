package com.seopport.kisvolumerank.dto.tokenAuto;

import lombok.Data;

@Data
public class OauthInfo {
    private String grant_type;
    private String appkey;
    private String appsecret;

    // getters and setters
}

