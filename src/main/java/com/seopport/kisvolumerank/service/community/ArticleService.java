package com.seopport.kisvolumerank.service.community;

import com.seopport.kisvolumerank.Entity.Article;
import com.seopport.kisvolumerank.dto.community.AddArticleRequest;
import com.seopport.kisvolumerank.dto.community.UpdateArticleRequest;
import com.seopport.kisvolumerank.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article save(AddArticleRequest request) {
        return articleRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findById(long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public void delete(long id) {
        articleRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }

    public List<Article> getArticlesByTicker(String ticker) {
        return articleRepository.findByTicker(ticker);
    }


}

