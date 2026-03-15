package com.example.myapplication.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.myapplication.social.dto.AddUserRequest;
import com.example.myapplication.social.dto.FollowRequest;
import com.example.myapplication.social.dto.PostRequest;
import com.example.myapplication.social.service.PlatformService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SocialPlatformControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlatformService platformService;

    @BeforeEach
    void setUp() {
        platformService.clearAll();
    }

    @Test
    void shouldNotifyFollowersOnPost() throws Exception {
        mockMvc.perform(post("/social/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AddUserRequest(1, "Alexander"))))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/social/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AddUserRequest(2, "Isabella"))))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/social/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new FollowRequest(2, 1))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.events[0]").value("Isabella is now following Alexander."));

        mockMvc.perform(post("/social/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostRequest(1, "Hiking in the mountains."))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.events[0]").value("Alexander posted: \"Hiking in the mountains.\"."))
                .andExpect(jsonPath("$.events[1]").value("Isabella received notification: Alexander posted: \"Hiking in the mountains.\"."));
    }
}
