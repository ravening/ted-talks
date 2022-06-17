package com.example.tedtalks.repository;

import com.example.tedtalks.models.TedTalk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TedTalkRepository extends JpaRepository<TedTalk, Long> {

    List<TedTalk> findByAuthorContainsIgnoreCase(String author);

    List<TedTalk> findByTitleContainsIgnoreCase(String title);

    List<TedTalk> findByViews(Long views);

    List<TedTalk> findByLikes(Long likes);
}
