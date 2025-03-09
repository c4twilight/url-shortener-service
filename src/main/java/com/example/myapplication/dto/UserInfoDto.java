package com.example.myapplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {
    @NotNull(message = "UserId is Required")
    private Long userId;

    @NotBlank(message = "Original URL cannot be empty")
    private String oldUrl;

    private String shortenUrl;
}
