package com.example.myapplication.social.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FollowRequest(
        @NotNull @Min(1) Integer followerId,
        @NotNull @Min(1) Integer followeeId
) {
}
