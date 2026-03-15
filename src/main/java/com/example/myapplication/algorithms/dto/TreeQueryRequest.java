package com.example.myapplication.algorithms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TreeQueryRequest(
        @NotEmpty(message = "par must not be empty") List<Integer> par,
        @NotEmpty(message = "query must not be empty") List<@NotNull List<@NotNull @Min(1) Integer>> query
) {
}
