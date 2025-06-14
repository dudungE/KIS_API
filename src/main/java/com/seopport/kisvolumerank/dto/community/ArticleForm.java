package com.seopport.kisvolumerank.dto.community;

import com.seopport.kisvolumerank.Entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class ArticleForm {
    private Long id;
    private String title; // 제목을 받을 필드
    private String content; // 내용을 받을 필드
    private String ticker; // 내용을 받을 필드

    public Article toEntity() {
        return new Article(title, content, ticker);
    }
}
