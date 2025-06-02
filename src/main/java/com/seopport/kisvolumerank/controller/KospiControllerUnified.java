package com.seopport.kisvolumerank.controller;

import com.seopport.kisvolumerank.dto.KospiIndexUnifiedDto;
import com.seopport.kisvolumerank.service.KospiServiceUnified;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kospi")
public class KospiControllerUnified {

    private final KospiServiceUnified kospiServiceUnified;

    @GetMapping("/daily")
    public ResponseEntity<List<KospiIndexUnifiedDto>> getDailyKospiData() {
        List<KospiIndexUnifiedDto> data = kospiServiceUnified.getKospiDailyIndices();
        if(data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleKospiException(RuntimeException e) {
        return ResponseEntity.internalServerError()
                .body("KOSPI 데이터 조회 실패: " + e.getMessage());
    }
}