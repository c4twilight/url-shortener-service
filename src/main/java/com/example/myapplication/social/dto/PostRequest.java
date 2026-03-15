package com.example.myapplication.social.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostRequest(
        @NotNull @Min(1) Integer userId,
        @NotBlank String content
) {
}
