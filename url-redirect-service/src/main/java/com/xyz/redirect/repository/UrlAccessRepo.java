package com.xyz.redirect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyz.redirect.entity.URLAccess;

import java.util.List;

@Repository
public interface UrlAccessRepo extends JpaRepository<URLAccess, Long> {
    @Query("SELECT u FROM url u WHERE u.fakeUrl = ?1")
    List<URLAccess> findUrlByFakeUrl(String fakeUrl);
}
