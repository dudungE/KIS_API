package com.seopport.kisvolumerank.controller.token;

import com.seopport.kisvolumerank.service.token.KisAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kis")
public class KisAuthController {

    private final KisAuthService kisAuthService;

    public KisAuthController(KisAuthService kisAuthService) {
        this.kisAuthService = kisAuthService;
    }

    @GetMapping("/token")
    public ResponseEntity<String> getToken() {
        String token = kisAuthService.getAccessToken();
        return ResponseEntity.ok(token);
    }
}

