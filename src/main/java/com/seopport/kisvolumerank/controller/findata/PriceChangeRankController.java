package com.seopport.kisvolumerank.controller.findata;

import com.seopport.kisvolumerank.dto.findata.PriceUpRankDto;
import com.seopport.kisvolumerank.service.findata.PriceUpRankSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


//@RestController
@Controller
public class PriceChangeRankController {

    private final PriceUpRankSerivce priceUpRankSerivce;


    @Autowired
    public PriceChangeRankController(PriceUpRankSerivce priceUpRankSerivce) {
        this.priceUpRankSerivce = priceUpRankSerivce;
    }

//    @GetMapping("/price-up-rank")
//    public Mono<List<PriceUpRankDto>> getVolumeRank() {
//        return priceUpRankSerivce.getPriceUpRank();
//    }

    // 페이지네이션
    @GetMapping("/price-up-rank")
    public String getPriceUpRank(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        List<PriceUpRankDto> allList = priceUpRankSerivce.getPriceUpRank().block(); // 100개 전체
        int totalElements = allList.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        int start = page * size;
        int end = Math.min(start + size, totalElements);
        List<PriceUpRankDto> pageContent = allList.subList(start, end);

        model.addAttribute("priceUpRankList", pageContent);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalElements);
        return "price-up-rank";
    }

}



