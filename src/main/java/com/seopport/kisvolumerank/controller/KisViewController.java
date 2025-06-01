package com.seopport.kisvolumerank.controller;

import com.seopport.kisvolumerank.dto.ResponseOutputDTO;
import com.seopport.kisvolumerank.service.KisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class KisViewController {

    private final KisService kisService;


    @Autowired
    public KisViewController(KisService kisService) {
        this.kisService = kisService;
    }

    @GetMapping("/volume-rank-view")
    public String getVolumeRankView(Model model) {
        // 비동기 데이터를 block()으로 동기 변환
        List<ResponseOutputDTO> volumeRankList = kisService.getVolumeRank().block();
        model.addAttribute("volumeRankList", volumeRankList);
        return "volume-rank"; // templates/volume-rank.html
    }

}
