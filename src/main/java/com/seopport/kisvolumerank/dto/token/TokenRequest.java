package com.seopport.kisvolumerank.dto.token;

import lombok.Data;

@Data
public class TokenRequest {
    private String grant_type = "client_credentials";
    private String appkey;
    private String appsecret;

    public TokenRequest(String appkey, String appsecret) {
        this.appkey = appkey;
        this.appsecret = appsecret;
    }

    // Getters and setters
}

