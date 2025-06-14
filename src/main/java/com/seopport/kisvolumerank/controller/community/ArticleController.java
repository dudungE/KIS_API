package com.seopport.kisvolumerank.controller.community;

import com.seopport.kisvolumerank.Entity.Article;
import com.seopport.kisvolumerank.dto.community.ArticleForm;
import com.seopport.kisvolumerank.repository.ArticleRepository;
import com.seopport.kisvolumerank.service.community.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleService articleService, ArticleRepository articleRepository) {
        this.articleService = articleService;
        this.articleRepository = articleRepository;
    }


    /**
     * Create
     */

    // 새게시글작성 화면
    @GetMapping("/articles/{ticker}/new")
    public String newArticleForm(@PathVariable String ticker, Model model) {
        model.addAttribute("ticker", ticker);
        return "articles/new";
    }


    // 게시글 생성 처리
    @PostMapping("/articles/{ticker}/create")
    public String createArticle(@PathVariable String ticker, // URL의 ticker (유효성 검사용)
                                @ModelAttribute ArticleForm form, // 폼 데이터가 DTO에 바인딩됨
                                RedirectAttributes redirectAttributes) {

        log.info("컨트롤러 - 폼 데이터 (DTO): {}", form);

        // URL의 티커와 폼 데이터의 티커 일치 여부 확인 (강화된 유효성 검사)
        if (!ticker.equals(form.getTicker())) {
            log.warn("컨트롤러 - URL의 티커({})와 폼 데이터의 티커({})가 일치하지 않습니다.", ticker, form.getTicker());
            redirectAttributes.addFlashAttribute("errorMessage", "티커 정보가 일치하지 않아 게시글을 생성할 수 없습니다.");
            return "redirect:/articles/" + ticker + "/new"; // 에러 메시지와 함께 폼으로 다시 리다이렉트
        }

        // 서비스 계층의 create 메서드 호출
        Article savedArticle = articleService.create(form); // DTO를 서비스로 전달

        if (savedArticle == null) {
            // 서비스 계층에서 유효성 검사 등에 실패하여 null을 반환한 경우
            log.warn("컨트롤러 - 게시글 생성 실패 (서비스 계층 검증 오류). DTO: {}", form);
            redirectAttributes.addFlashAttribute("errorMessage", "게시글 생성에 실패했습니다. 유효한 정보를 입력해주세요.");
            return "redirect:/articles/" + ticker + "/new";
        }

        log.info("컨트롤러 - 게시글 생성 완료: {}", savedArticle);

        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 작성되었습니다!");

        // 저장된 게시글의 상세 페이지로 리다이렉트
        return "redirect:/stock/" + savedArticle.getTicker();
    }



    /*
     *
     * Read
     *
     * */


    @GetMapping("/articles/{ticker}/community")
    public String getCommunity(@PathVariable String ticker, Model model, HttpServletRequest request) {
        List<Article> articles = articleService.getArticlesByTicker(ticker);
        model.addAttribute("articles", articles);
        // 현재 URI를 model에 담아 전달
        model.addAttribute("currentUri", request.getRequestURI());
        return "articles/community";
    }

    // --- 개별 글 조회 (showArticle) 메서드 (수정됨: 정규 표현식 추가) ---
    @GetMapping("/articles/{id:[0-9]+}") // <-- 핵심 수정: {id}가 숫자만 오도록 정규 표현식 추가
    public String showArticle(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("게시글 상세 페이지 요청됨. 게시글 ID: {}", id);

        // 1. 서비스 계층을 통해 ID로 게시글 조회
        Article article = articleService.getArticleById(id);

        // 2. 조회된 게시글이 없는 경우 처리
        if (article == null) {
            log.warn("ID {}에 해당하는 게시글을 찾을 수 없습니다.", id);
            redirectAttributes.addFlashAttribute("errorMessage", "요청하신 게시글을 찾을 수 없습니다.");
            return "redirect:/"; // 게시글을 찾지 못하면 홈으로 리다이렉트
        }

        // 3. 조회된 게시글을 모델에 추가하여 뷰로 전달
        model.addAttribute("article", article);
        log.info("게시글 상세 정보 전달: {}", article);

        // 4. "articles/show.html" 템플릿 반환
        return "articles/show";
    }


    /**
     * Update
     */


    // 게시글 수정 폼 페이지 (ID만 받음)
    @GetMapping("/articles/{id}/edit") // <-- Ticker 제거
    public String editArticleForm(@PathVariable Long id, // <-- Ticker 제거
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        log.info("게시글 수정 폼 요청: ID={}", id); // 로그 수정

        Article article = articleService.getArticleById(id); // ID로만 조회

        if (article == null) { // 게시글이 없으면
            log.warn("수정 대상 게시글을 찾을 수 없습니다. ID={}", id);
            redirectAttributes.addFlashAttribute("errorMessage", "수정하려는 게시글을 찾을 수 없습니다.");
            return "redirect:/"; // 적절한 리다이렉션 경로로 변경 (예: /articles)
        }

        model.addAttribute("article", article); // 수정 폼에 기존 데이터를 채우기 위해 전달
        // model.addAttribute("ticker", article.getTicker()); // <-- 중요: ticker를 article 객체에서 가져와 전달
        // 이제 템플릿의 ticker는 article.ticker에서 직접 가져오게 되므로 별도의 ticker 모델 속성은 필수가 아님
        // 하지만 명시적으로 보내면 템플릿에서 th:text="${ticker}"처럼 사용할 수 있어 편리할 수 있습니다.
        // 현재 템플릿에 ticker를 사용하고 있으니, article.ticker에서 가져와 전달하는 것이 좋습니다.
        model.addAttribute("ticker", article.getTicker()); // 템플릿의 H2 제목에 티커 표시를 위해 전달

        return "articles/edit"; // resources/templates/articles/edit.html 로 이동
    }

    // 게시글 업데이트 처리
    // /articles/{id}로 POST 요청을 받습니다. (_method=put 또는 _method=patch 필요)
    @PostMapping("/articles/{id}")
    public String updateArticle(@PathVariable Long id,
                                @ModelAttribute ArticleForm form, // 업데이트할 데이터는 DTO에 바인딩
                                RedirectAttributes redirectAttributes) {
        log.info("게시글 업데이트 요청 처리: URL ID={}, Form: {}", id, form);

        form.setId(id); // URL의 ID를 폼 DTO에 설정 (신뢰성 높은 URL ID 사용)

        // 서비스 계층의 update 메서드 호출 (서비스 메서드 시그니처가 ID와 Form DTO만 받도록 수정해야 함)
        // ArticleService.update(Long id, ArticleForm dto) 형태로 가정
        Article updatedArticle = articleService.update(id, form);

        if (updatedArticle == null) {
            log.warn("게시글 업데이트 실패. ID={}, Form: {}", id, form);
            redirectAttributes.addFlashAttribute("errorMessage", "게시글 업데이트에 실패했습니다. 유효한 정보를 입력해주세요.");
            return "redirect:/articles/" + id + "/edit"; // 실패 시 수정 폼으로 다시 리다이렉트
        }

        log.info("게시글 업데이트 완료: {}", updatedArticle);
        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 업데이트되었습니다!");

        // 업데이트 후 해당 게시글의 상세 페이지로 리다이렉트
        return "redirect:/articles/" + updatedArticle.getId();
    }




    /**
     * Delete
     */

    // 게시글 삭제 처리
    // /articles/{id}/delete 로 POST 요청 (실제 HTTP DELETE를 흉내 내기 위해 _method=delete 사용)
    @PostMapping("/articles/{id}/delete") // <-- 삭제 URL 경로 명확화
    public String deleteArticle(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        log.info("게시글 삭제 요청 처리: ID={}", id);

        // 티커 조회
        Article articleToDelete = articleService.getArticleById(id);
        String tickerOfDeletedArticle = articleToDelete.getTicker();


        // 서비스 계층의 delete 메서드 호출 (시그니처 변경에 맞춰 수정)
        boolean deleted = articleService.delete(id); // <-- 호출 방식 변경

        if (deleted) {
            log.info("게시글 삭제 성공. ID: {}", id);
            log.info("티커정보 {}", tickerOfDeletedArticle);
            redirectAttributes.addFlashAttribute("message", id + "번 게시글이 성공적으로 삭제되었습니다.");
            // 삭제 후 일반적으로 모든 게시글 목록이나 홈으로 리다이렉트
            // 이제 티커 정보를 바로 알 수 없으므로, 티커별 목록으로 리다이렉트하려면 DB 조회가 필요합니다.
            // 가장 일반적인 리다이렉트는 홈 또는 모든 게시글 목록입니다.
            return "redirect:/stock/" + tickerOfDeletedArticle; // 홈 페이지로 리다이렉트
        } else {
            log.warn("게시글 삭제 실패. ID: {}", id);
            redirectAttributes.addFlashAttribute("errorMessage", "게시글 삭제에 실패했습니다. 올바른 게시글인지 확인해주세요.");
            return "redirect:/articles/" + id; // 실패 시 상세 페이지로 다시 리다이렉트
        }
    }
}

