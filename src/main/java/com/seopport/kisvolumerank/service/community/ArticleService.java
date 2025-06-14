package com.seopport.kisvolumerank.service.community;

import com.seopport.kisvolumerank.Entity.Article;
import com.seopport.kisvolumerank.dto.community.ArticleForm;
import com.seopport.kisvolumerank.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }


    /*
    *
    *
    * Createw
    * */

    @Transactional // 트랜잭션 단위로 묶어주는 것이 좋습니다.
    public Article create(ArticleForm dto) {
        // 1. DTO를 엔티티로 변환합니다.
        Article article = dto.toEntity();
        log.info("서비스 계층 - DTO로부터 변환된 엔티티: {}", article);

        // 2. 유효성 검사 또는 특정 비즈니스 로직 추가
        // 만약 ticker가 반드시 있어야만 생성되도록 하려면 (일반적인 커뮤니티 게시글 흐름)
        if (article.getTicker() == null || article.getTicker().trim().isEmpty()) {
            log.warn("서비스 계층 - Ticker가 없거나 유효하지 않아 게시글을 생성할 수 없습니다. DTO: {}", dto);
            return null; // 또는 예외를 던져 컨트롤러에서 처리하도록 합니다.
        }

        // 기존 코드: if (article.getTicker() != null) { return null; }
        // 이 조건문은 ticker가 있을 때 생성을 막는 것으로 보입니다.
        // 현재는 ticker가 필수라고 가정하고, 위에서 ticker가 없을 때 null을 반환하도록 수정했습니다.
        // 만약 "ticker가 없어야만 게시글 생성"이 의도라면, 아래와 같이 바꿔야 합니다.
        /*
        if (article.getTicker() != null && !article.getTicker().trim().isEmpty()) {
            log.warn("서비스 계층 - Ticker가 존재하여 게시글을 생성할 수 없습니다. DTO: {}", dto);
            return null; // Ticker가 있으면 생성하지 않음
        }
        */

        // 3. 엔티티를 데이터베이스에 저장하고 저장된 엔티티를 반환합니다.
        Article savedArticle = articleRepository.save(article);
        log.info("서비스 계층 - 저장 완료된 엔티티: {}", savedArticle);
        return savedArticle;
    }


    /*
    *
    * Read Show
    *
    * */

    public List<Article> getArticlesByTicker(String ticker) {
        return articleRepository.findByTicker(ticker);
    }


    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    // --- 조회 메서드 추가 (컨트롤러에서 사용하기 위해) ---
    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElse(null); // 실제로는 예외 처리 권장
    }



    /*
    *
    *  Update
    * */

    // --- 게시글 업데이트 메서드 (수정됨) ---
    @Transactional
    public Article update(Long id, ArticleForm dto) { // <-- PathVariable tickerFromPath 인자 제거
        log.info("서비스 계층 - 게시글 업데이트 요청: ID={}, DTO: {}", id, dto);

        // 1. 주어진 ID로 기존 게시글을 조회합니다.
        Article target = articleRepository.findById(id)
                .orElse(null); // 게시글이 없으면 null 반환 (예외 처리로 변경 권장)

        // 2. 유효성 검사
        // 2-1. 게시글 존재 여부 확인
        if (target == null) {
            log.warn("서비스 계층 - 업데이트 대상 게시글을 찾을 수 없습니다. ID: {}", id);
            return null; // 또는 예외를 던짐
        }

        // 2-2. DTO에 ticker가 포함되어 넘어왔고, 기존 게시글의 ticker와 다를 경우 (옵션)
        // 이 로직은 비즈니스 요구사항에 따라 달라집니다.
        // - 티커 변경 불가: if (dto.getTicker() != null && !Objects.equals(target.getTicker(), dto.getTicker())) { return null; }
        // - 티커 변경 가능: target.setTicker(dto.getTicker());
        // 여기서는 티커는 변경 불가능하고, 단순히 폼 데이터로 넘어온 티커와 기존 티커의 일치 여부만 확인한다고 가정합니다.
        if (dto.getTicker() != null && !Objects.equals(target.getTicker(), dto.getTicker())) {
            log.warn("서비스 계층 - 게시글의 티커({})와 폼 데이터의 티커({})가 일치하지 않습니다. 업데이트 불가. ID: {}", target.getTicker(), dto.getTicker(), id);
            return null; // 티커가 변경되면 업데이트 실패
        }


        // 3. DTO에서 받은 데이터로 기존 게시글의 필드를 업데이트합니다.
        //    (ID는 이미 검증되었고, 여기서는 title과 content를 업데이트합니다.)
        target.setTitle(dto.getTitle());
        target.setContent(dto.getContent());
        // target.setTicker(dto.getTicker()); // <-- 만약 업데이트 시 티커 변경도 허용한다면 이 줄을 추가

        // 4. 변경된 엔티티를 데이터베이스에 저장
        Article updatedArticle = articleRepository.save(target);
        log.info("서비스 계층 - 게시글 업데이트 완료: {}", updatedArticle);
        return updatedArticle;
    }



    // --- 게시글 삭제 메서드 (수정됨) ---
    @Transactional
    public boolean delete(Long id) { // <-- tickerFromPath 인자 제거
        log.info("서비스 계층 - 게시글 삭제 요청: ID={}", id);

        Article target = articleRepository.findById(id)
                .orElse(null);

        if (target == null) {
            log.warn("서비스 계층 - 삭제 대상 게시글을 찾을 수 없습니다. ID: {}", id);
            return false;
        }

        articleRepository.delete(target);
        log.info("서비스 계층 - 게시글 삭제 완료: {}", target);
        return true;
    }








}

