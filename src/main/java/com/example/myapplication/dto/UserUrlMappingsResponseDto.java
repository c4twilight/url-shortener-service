package com.example.myapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUrlMappingsResponseDto {
    private Long userId;
    private List<UrlMappingDto> urlMappings;
}

