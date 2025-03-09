package com.example.myapplication.repository;

import com.example.myapplication.dto.UserInfoDto;
import com.example.myapplication.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByShortUrl(String shortUrl);
    List<UrlMapping> findAllByUserId(Long userId);
    Optional<UrlMapping> findByUserIdAndOldUrl(Long userId, String oldUrl);


}
