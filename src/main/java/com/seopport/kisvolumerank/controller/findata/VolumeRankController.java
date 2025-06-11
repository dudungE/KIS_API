package com.seopport.kisvolumerank.controller.findata;

import com.seopport.kisvolumerank.dto.findata.VolumeRankDto;
import com.seopport.kisvolumerank.service.findata.VolumeRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


    // 데이터 100개 통으로 가져오기
//    @GetMapping("/volume-rank-view2")
//    public String getVolumeRankView(Model model) {
//        // 비동기 데이터를 block()으로 동기 변환
//        List<VolumeRankDto> volumeRankList = volumeRankService.getVolumeRank().block();
//        model.addAttribute("volumeRankList", volumeRankList);
//        return "volume-rank2"; // templates/volume-rank.html
//    }


    // 페이지네이션
    @GetMapping("/volume-rank-view2")
    public String getVolumeRankView(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        List<VolumeRankDto> allList = volumeRankService.getVolumeRank().block(); // 100개 전체
        int totalElements = allList.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        int start = page * size;
        int end = Math.min(start + size, totalElements);
        List<VolumeRankDto> pageContent = allList.subList(start, end);

        model.addAttribute("volumeRankList", pageContent);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalElements);
        return "volume-rank2";
    }



}
