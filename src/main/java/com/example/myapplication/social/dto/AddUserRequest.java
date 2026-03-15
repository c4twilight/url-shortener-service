package com.example.myapplication.social.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddUserRequest(
        @NotNull @Min(1) Integer id,
        @NotBlank String name
) {
}
