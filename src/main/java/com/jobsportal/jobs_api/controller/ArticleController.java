package com.jobsportal.jobs_api.controller;

import com.jobsportal.jobs_api.entity.Article;
import com.jobsportal.jobs_api.repository.ArticleRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*") // allow frontend to access locally
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    // Fetch all articles
    @GetMapping
    public List<Article> getAllArticles() {
        return articleRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"));
    }

    // Fetch articles by author
    @GetMapping("/author/{author}")
    public List<Article> getArticlesByAuthor(@PathVariable String author) {
        return articleRepository.findByAuthor(author);
    }

    // Fetch articles by title containing a keyword
    @GetMapping("/search/{keyword}")
    public List<Article> getArticlesByKeyword(@PathVariable String keyword) {
        return articleRepository.findByTitleContainingIgnoreCase(keyword);
    }

    // Admin-only: create new article
    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        return articleRepository.save(article);
    }

    // Fetch article by ID
    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable Long id) {
        Article article = null;
        try {
            article = articleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Article not found"));
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return article;
    }

    // Upvote/Downvote
    @PostMapping("/{id}/vote")
    public ResponseEntity<Article> voteArticle(@PathVariable Long id, @RequestParam String type) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        if ("upvote".equalsIgnoreCase(type)) article.setUpvotes(article.getUpvotes() + 1);
        else if ("downvote".equalsIgnoreCase(type)) article.setDownvotes(article.getDownvotes() + 1);

        articleRepository.save(article);
        return ResponseEntity.ok(article);
    }

    // Fetch current upvotes and downvotes for an article
    @GetMapping("/{id}/votes")
    public ResponseEntity<?> getArticleVotes(@PathVariable Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        // Return only votes
        return ResponseEntity.ok(new VotesResponse(article.getUpvotes(), article.getDownvotes()));
    }

    // Inner static class for votes response
    public static class VotesResponse {
        private int upvotes;
        private int downvotes;

        public VotesResponse(int upvotes, int downvotes) {
            this.upvotes = upvotes;
            this.downvotes = downvotes;
        }

        public int getUpvotes() {
            return upvotes;
        }

        public int getDownvotes() {
            return downvotes;
        }
    }


}
