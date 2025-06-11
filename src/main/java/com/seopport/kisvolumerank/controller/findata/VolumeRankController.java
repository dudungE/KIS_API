package com.seopport.kisvolumerank.controller.findata;

import com.seopport.kisvolumerank.dto.findata.VolumeRankDto;
import com.seopport.kisvolumerank.service.findata.VolumeRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class VolumeRankController {

    private VolumeRankService volumeRankService;

    @Autowired
    public VolumeRankController(VolumeRankService volumeRankService) {
        this.volumeRankService = volumeRankService;
    }

    // @RestController
//    @GetMapping("/volume-rank2")
//    public Mono<List<VolumeRankDto>> getVolumeRank() {
//        return volumeRankService.getVolumeRank();
//    }

    @GetMapping("/volume-rank-view2")
    public String getVolumeRankView(Model model) {
        // 비동기 데이터를 block()으로 동기 변환
        List<VolumeRankDto> volumeRankList = volumeRankService.getVolumeRank().block();
        model.addAttribute("volumeRankList", volumeRankList);
        return "volume-rank2"; // templates/volume-rank.html
    }

}
