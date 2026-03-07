package com.example.myapplication.service;

import com.example.myapplication.dto.UrlMappingDto;
import com.example.myapplication.dto.UserInfoDto;
import com.example.myapplication.dto.UserUrlMappingsResponseDto;
import com.example.myapplication.entity.UrlMapping;
import com.example.myapplication.exception.ShortUrlNotFoundException;
import com.example.myapplication.repository.UrlRepository;
import com.example.myapplication.strategy.ShortUrlGenerator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private static final Logger log = LoggerFactory.getLogger(UrlShortenerServiceImpl.class);

    private final UrlRepository urlRepository;
    private final ShortUrlGenerator shortUrlGenerator;
    //private final ZooKeeperIdGenerator idGenerator;

    public UrlShortenerServiceImpl(UrlRepository urlRepository, ShortUrlGenerator shortUrlGenerator ){ //, ZooKeeperIdGenerator idGenerator
        this.urlRepository = urlRepository;
        this.shortUrlGenerator = shortUrlGenerator;
        //this.idGenerator = idGenerator;
    }

    @Override
    public Optional<UserInfoDto> findMapping(Long userId, String oldUrl) {
        return urlRepository.findByUserIdAndOldUrl(userId, oldUrl)
                .map(mapping -> UserInfoDto.builder()
                        .userId(mapping.getUserId())
                        .oldUrl(mapping.getOldUrl())
                        .shortenUrl(mapping.getShortUrl())
                        .build());
    }

    @Override
    @Transactional
    public UserInfoDto createShortUrl(UserInfoDto userInfoDto) {
        // Create a new mapping if not already present
        UrlMapping mapping = UrlMapping.builder()
                .userId(userInfoDto.getUserId())
                .oldUrl(userInfoDto.getOldUrl())
                .build();
        mapping = urlRepository.save(mapping); // Generate auto-generated id

        // Generate the short URL using Base62 encoding strategy
        String shortCode = shortUrlGenerator.generateShortUrl(mapping.getId());
        mapping.setShortUrl(shortCode);

        // Update the mapping with the generated shortUrl
        mapping = urlRepository.save(mapping);
        log.info("Created short URL mapping for userId={} shortCode={}", mapping.getUserId(), mapping.getShortUrl());

        return UserInfoDto.builder()
                .userId(mapping.getUserId())
                .oldUrl(mapping.getOldUrl())
                .shortenUrl(mapping.getShortUrl())
                .build();
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        Optional<UrlMapping> mappingOpt = urlRepository.findByShortUrl(shortUrl);
        return mappingOpt.map(UrlMapping::getOldUrl)
                .orElseThrow(() -> new ShortUrlNotFoundException(shortUrl));
    }

    @Override
    public UserUrlMappingsResponseDto getUrlMappingsByUserId(Long userId) {
        List<UrlMapping> mappings = urlRepository.findAllByUserId(userId);
        List<UrlMappingDto> mappingDtos = mappings.stream()
                .map(mapping -> new UrlMappingDto(mapping.getOldUrl(), mapping.getShortUrl()))
                .collect(Collectors.toList());
        return new UserUrlMappingsResponseDto(userId, mappingDtos);
    }

    /*public UserInfoDto shortenUrl(UserInfoDto userInfoDto) throws KeeperException, InterruptedException {
        // Step 1: Generate unique ID using ZooKeeper
        String uniqueId = idGenerator.generateId();

        // Step 2: Convert ID to Base62 encoding
        String shortUrl = shortUrlGenerator.generateShortUrl(Long.parseLong(uniqueId)); //Base62.encode(Long.parseLong(uniqueId));

        // Step 3: Save to database
        UrlMapping mapping = UrlMapping.builder()
                .userId(userInfoDto.getUserId())
                .oldUrl(userInfoDto.getOldUrl())
                .shortUrl(shortUrl)
                .build();
        urlRepository.save(mapping);
        return UserInfoDto.builder()
                .userId(mapping.getUserId())
                .oldUrl(mapping.getOldUrl())
                .shortenUrl(mapping.getShortUrl())
                .build();
    }
     */
}

