package com.constitution.awareness.repository;

import com.constitution.awareness.entity.Article;
import com.constitution.awareness.entity.User;
import com.constitution.awareness.entity.UserArticleActivity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserArticleActivityRepository
        extends JpaRepository<UserArticleActivity, Long> {


    Optional<UserArticleActivity>
    findByUserAndArticle(

            User user,

            Article article

    );


    List<UserArticleActivity>
    findByUserOrderByLastViewedAtDesc(

            User user,

            Pageable pageable

    );
}