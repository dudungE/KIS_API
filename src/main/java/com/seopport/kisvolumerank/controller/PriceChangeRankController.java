package com.seopport.kisvolumerank.controller;

import com.seopport.kisvolumerank.dto.PriceChangeRankDto;
import com.seopport.kisvolumerank.service.PriceChangeRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PriceChangeRankController {

    private final PriceChangeRankService priceChangeRankService;


    @Autowired
    public PriceChangeRankController(PriceChangeRankService priceChangeRankService) {
        this.priceChangeRankService = priceChangeRankService;
    }

    @GetMapping("/price-change-rank")
    public String getPriceChangeRank(Model model) {
        // 비동기 데이터를 block()으로 동기 변환
        List<PriceChangeRankDto> priceChangeRankList = priceChangeRankService.getPriceChangeRank().block();
        model.addAttribute("priceChangeRankList", priceChangeRankList);
        return "price-change-rank";
    }

}
