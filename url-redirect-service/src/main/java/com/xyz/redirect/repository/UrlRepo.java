package com.xyz.redirect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyz.redirect.entity.URL;

import java.util.List;

@Repository
public interface UrlRepo extends JpaRepository<URL, Long> {
    @Query("SELECT u FROM url u WHERE u.refId = ?1")
    List<URL> findUrlByRefID(long refId);
}
