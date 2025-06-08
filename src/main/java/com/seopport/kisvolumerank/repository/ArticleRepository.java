package com.seopport.kisvolumerank.repository;

import com.seopport.kisvolumerank.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    // 티커로 필터링
    List<Article> findByTicker(String ticker);

}

