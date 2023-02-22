package com.xyz.shortener.repository;

import com.xyz.shortener.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepo extends JpaRepository<URL, Long> {
    @Query("SELECT u FROM url u WHERE u.realUrl = ?1 AND u.userId = ?2")
    List<URL> findUrlByRealUrl(String realUrl, Integer userId);

    @Query("SELECT u FROM url u WHERE u.userId = ?1")
    List<URL> findAllUrlByUserId(Integer userId);
}
