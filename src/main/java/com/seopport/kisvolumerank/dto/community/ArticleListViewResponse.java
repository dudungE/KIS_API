package com.seopport.kisvolumerank.dto.community;

import com.seopport.kisvolumerank.Entity.Article;
import lombok.Data;
import lombok.Generated;

@Data
public class ArticleListViewResponse {
    private final Long id;
    private final String ticker;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.ticker = article.getTicker();
        this.title = article.getTitle();
        this.content = article.getContent();
    }

//    @Generated
//    public Long getId() {
//        return this.id;
//    }
//
//    @Generated
//    public String getTicker() {
//        return this.ticker;
//    }
//
//    @Generated
//    public String getTitle() {
//        return this.title;
//    }
//
//    @Generated
//    public String getContent() {
//        return this.content;
//    }
}
