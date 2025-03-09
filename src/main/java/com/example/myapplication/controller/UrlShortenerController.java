package com.example.myapplication.controller;

import com.example.myapplication.dto.UserInfoDto;
import com.example.myapplication.dto.UserUrlMappingsResponseDto;
import com.example.myapplication.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.apache.zookeeper.KeeperException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/s")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping
    public ResponseEntity<UserInfoDto> createShortUrl(@Valid @RequestBody UserInfoDto userInfoDto) {
        Optional<UserInfoDto> existingMapping = urlShortenerService.findMapping(userInfoDto.getUserId(), userInfoDto.getOldUrl());
        if(existingMapping.isPresent()){
            return ResponseEntity.ok(existingMapping.get());
        } else {
            UserInfoDto createdMapping = urlShortenerService.createShortUrl(userInfoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMapping);
        }
    }

//    @PostMapping("/v2")
//    public ResponseEntity<UserInfoDto> createShortUrl1(@Valid @RequestBody UserInfoDto userInfoDto) {
//        Optional<UserInfoDto> existingMapping = urlShortenerService.findMapping(userInfoDto.getUserId(), userInfoDto.getOldUrl());
//        if(existingMapping.isPresent()){
//            return ResponseEntity.ok(existingMapping.get());
//        } else {
//            UserInfoDto createdMapping = null;
//            try {
//                createdMapping = urlShortenerService.shortenUrl(userInfoDto);
//            } catch (KeeperException |InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdMapping);
//        }
//    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortUrl) {
        return ResponseEntity.ok(urlShortenerService.getOriginalUrl(shortUrl));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserUrlMappingsResponseDto> getUrlMappingsByUserId(@PathVariable Long userId) {
        UserUrlMappingsResponseDto response = urlShortenerService.getUrlMappingsByUserId(userId);
        return ResponseEntity.ok(response);
    }
}
