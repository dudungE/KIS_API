package com.seopport.kisvolumerank.controller.community;


import com.seopport.kisvolumerank.Entity.Article;
import com.seopport.kisvolumerank.dto.community.ArticleListViewResponse;
import com.seopport.kisvolumerank.dto.community.ArticleViewResponse;
import com.seopport.kisvolumerank.service.community.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogViewController {
    private final ArticleService articleService;

    @Generated
    public BlogViewController(ArticleService blogService) {
        this.articleService = blogService;
    }


    @GetMapping({"/articles"})
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = this.articleService.findAll().stream().map(ArticleListViewResponse::new).toList();
        model.addAttribute("articles", articles);
        return "articleList";
    }

    @GetMapping({"/articles/{id}"})
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = this.articleService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));
        return "article";
    }

    @GetMapping({"/new-article"})
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = this.articleService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }

//    @GetMapping("/stock/{ticker}/community")
//    public String getCommunity(@PathVariable String ticker, Model model) {
//        List<Article> articles = articleService.getArticlesByTicker(ticker);
//        model.addAttribute("articles", articles);
//        return "community";
//    }

    @GetMapping("/stock/{ticker}/community")
    public String getCommunity(@PathVariable String ticker, Model model, HttpServletRequest request) {
        List<Article> articles = articleService.getArticlesByTicker(ticker);
        model.addAttribute("articles", articles);
        // 현재 URI를 model에 담아 전달
        model.addAttribute("currentUri", request.getRequestURI());
        return "community";
    }


}
