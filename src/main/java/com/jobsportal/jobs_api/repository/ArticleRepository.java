package com.jobsportal.jobs_api.repository;

import com.jobsportal.jobs_api.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    // Find articles by exact author name
    List<Article> findByAuthor(String author);

    // Find articles whose title contains the keyword (case-insensitive)
    List<Article> findByTitleContainingIgnoreCase(String keyword);

}
