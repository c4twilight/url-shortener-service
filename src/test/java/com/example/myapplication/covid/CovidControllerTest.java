package com.example.myapplication.covid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.myapplication.covid.model.Covid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:cleanup.sql", "classpath:data.sql"})
class CovidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreation() throws Exception {
        Covid expectedRecord = new Covid(null, "Test Country", 100, 5, 50);

        mockMvc.perform(post("/covid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedRecord)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.country", is("Test Country")))
                .andExpect(jsonPath("$.active", is(100)))
                .andExpect(jsonPath("$.death", is(5)))
                .andExpect(jsonPath("$.recovered", is(50)));
    }

    @Test
    void testGetCovidById() throws Exception {
        mockMvc.perform(get("/covid/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.country", is("country1")))
                .andExpect(jsonPath("$.active", is(20)))
                .andExpect(jsonPath("$.death", is(1)))
                .andExpect(jsonPath("$.recovered", is(2)));
    }

    @Test
    void testGetCovidByIdNotFound() throws Exception {
        mockMvc.perform(get("/covid/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testTop5ByDeath() throws Exception {
        mockMvc.perform(get("/covid/top5").param("by", "death"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].country", is("country4")))
                .andExpect(jsonPath("$[0].death", is(7)))
                .andExpect(jsonPath("$[1].country", is("country2")))
                .andExpect(jsonPath("$[1].death", is(6)))
                .andExpect(jsonPath("$[2].country", is("country5")))
                .andExpect(jsonPath("$[2].death", is(5)))
                .andExpect(jsonPath("$[3].country", is("country7")))
                .andExpect(jsonPath("$[3].death", is(4)))
                .andExpect(jsonPath("$[4].country", is("country3")))
                .andExpect(jsonPath("$[4].death", is(3)));
    }

    @Test
    void testTop5ByInvalidAttribute() throws Exception {
        mockMvc.perform(get("/covid/top5").param("by", "country"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testTotalByActive() throws Exception {
        mockMvc.perform(get("/covid/total").param("by", "active"))
                .andExpect(status().isOk())
                .andExpect(content().string("1539"));
    }

    @Test
    void testTotalByDeath() throws Exception {
        mockMvc.perform(get("/covid/total").param("by", "death"))
                .andExpect(status().isOk())
                .andExpect(content().string("28"));
    }

    @Test
    void testTotalByRecovered() throws Exception {
        mockMvc.perform(get("/covid/total").param("by", "recovered"))
                .andExpect(status().isOk())
                .andExpect(content().string("177"));
    }

    @Test
    void testTotalByInvalidAttribute() throws Exception {
        mockMvc.perform(get("/covid/total").param("by", "country"))
                .andExpect(status().isBadRequest());
    }
}
