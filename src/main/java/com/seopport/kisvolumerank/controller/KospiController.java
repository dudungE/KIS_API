package com.seopport.kisvolumerank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seopport.kisvolumerank.dto.IndexData;
import com.seopport.kisvolumerank.dto.KospiIndexDailyDto;
import com.seopport.kisvolumerank.service.KospiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class KospiController {

    private final KospiService kospiService;

    @GetMapping("/kospi/daily")
    public String kospiDailyView(Model model) {
        List<KospiIndexDailyDto> kospiList = kospiService.getKospiDailyIndices();
        model.addAttribute("kospiList", kospiList);
        return "kospi-daily"; // templates/kospi-daily.html
    }
}
