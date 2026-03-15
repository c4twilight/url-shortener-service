package com.example.myapplication.algorithms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.myapplication.algorithms.dto.TreeQueryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TreeQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSolveTreeQueries() throws Exception {
        TreeQueryRequest request = new TreeQueryRequest(
                List.of(-1, 1, 1, 2, 2, 3),
                List.of(List.of(1, 1), List.of(2, 3), List.of(3, 2), List.of(5, 2))
        );

        mockMvc.perform(post("/algorithms/tree-queries/solve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answers[0]").value(1))
                .andExpect(jsonPath("$.answers[1]").value(5))
                .andExpect(jsonPath("$.answers[2]").value(6))
                .andExpect(jsonPath("$.answers[3]").value(-1));
    }
}
