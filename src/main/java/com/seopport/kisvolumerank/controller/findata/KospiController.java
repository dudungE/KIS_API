package com.seopport.kisvolumerank.controller.findata;

import com.seopport.kisvolumerank.dto.findata.KospiIndexDailyDto;
import com.seopport.kisvolumerank.service.findata.KospiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
