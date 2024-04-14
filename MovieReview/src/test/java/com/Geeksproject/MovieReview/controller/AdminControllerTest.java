package com.Geeksproject.MovieReview.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.Geeksproject.MovieReview.entity.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    public void setup()
    {
    	
    }

    @Test
    public void testAddMovie() throws Exception {
        // Create a sample Movie object to send in the request
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setRating(4.5);

        // Convert the Movie object to JSON string
        String movieJson = objectMapper.writeValueAsString(movie);

        // Perform a POST request to add a movie
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addmovie/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Movie"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(4.5));
    }
}
