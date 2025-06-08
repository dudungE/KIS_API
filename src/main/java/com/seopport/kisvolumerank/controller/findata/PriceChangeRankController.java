package com.seopport.kisvolumerank.controller.findata;

import com.seopport.kisvolumerank.dto.findata.PriceChangeRankDto;
import com.seopport.kisvolumerank.service.findata.PriceChangeRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PriceChangeRankController {

    private final PriceChangeRankService priceChangeRankService;


    @Autowired
    public PriceChangeRankController(PriceChangeRankService priceChangeRankService) {
        this.priceChangeRankService = priceChangeRankService;
    }


    // getPrdy_vrss_sign가 2인경우만 필터링(급상승)
    @GetMapping("/price-up-rank")
    public String getPriceUpRank(Model model) {
        // 비동기 데이터를 block()으로 동기 변환
        List<PriceChangeRankDto> priceChangeRankList = priceChangeRankService.getPriceChangeRank().block().stream()
                .filter(item -> "2".equals(item.getPrdy_vrss_sign()))
                .collect(Collectors.toList());

        model.addAttribute("priceChangeRankList", priceChangeRankList);
        return "price-up-rank";
    }

    // getPrdy_vrss_sign가 1인경우만 필터링(급하락)
    @GetMapping("/price-down-rank")
    public String getPriceDownRank(Model model) {
        // 비동기 데이터를 block()으로 동기 변환
        List<PriceChangeRankDto> priceChangeRankList = priceChangeRankService.getPriceChangeRank().block().stream()
                .filter(item -> "1".equals(item.getPrdy_vrss_sign()))
                .collect(Collectors.toList());

        model.addAttribute("priceChangeRankList", priceChangeRankList);
        return "price-down-rank";
    }

}
