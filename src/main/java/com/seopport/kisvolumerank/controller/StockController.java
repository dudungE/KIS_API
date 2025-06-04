package com.seopport.kisvolumerank.controller;

import com.seopport.kisvolumerank.dto.StockPriceResponse;
import com.seopport.kisvolumerank.service.KisService;
import com.seopport.kisvolumerank.service.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stock/{iscd}")
    public String getStock(@PathVariable String iscd, Model model) {
        StockPriceResponse response = stockService.getStockPrice(iscd);
        model.addAttribute("stock", response.getOutput());
        model.addAttribute("message", response.getMsg1());
        return "stock-detail";
    }

}
