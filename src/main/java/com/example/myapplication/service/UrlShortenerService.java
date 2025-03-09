package com.example.myapplication.service;

import com.example.myapplication.dto.UserInfoDto;
import com.example.myapplication.dto.UserUrlMappingsResponseDto;
import org.apache.zookeeper.KeeperException;

import java.util.List;
import java.util.Optional;

public interface UrlShortenerService {
    UserInfoDto createShortUrl(UserInfoDto userInfoDto);
//    UserInfoDto shortenUrl(UserInfoDto userInfoDto)throws KeeperException, InterruptedException;
    String getOriginalUrl(String shortUrl);
    // In UrlShortenerService.java
    Optional<UserInfoDto> findMapping(Long userId, String oldUrl);
    UserUrlMappingsResponseDto getUrlMappingsByUserId(Long userId);

}
