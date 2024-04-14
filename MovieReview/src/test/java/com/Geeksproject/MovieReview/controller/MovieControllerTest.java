package com.Geeksproject.MovieReview.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import com.Geeksproject.MovieReview.dto.MovieDto;
import com.Geeksproject.MovieReview.dto.ReviewDto;
import com.Geeksproject.MovieReview.enums.Genre;
import com.Geeksproject.MovieReview.entity.Review;
import com.Geeksproject.MovieReview.service.MovieService;
import com.Geeksproject.MovieReview.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieService movieService;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void testGetMovie() throws Exception {
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Test Movie");
        movieDto.setRating(4.5);

        when(movieService.getMovie("Test Movie")).thenReturn(movieDto);

        mockMvc.perform(get("/movie/getMovie")
                .param("movieName", "Test Movie"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Movie"))
                .andExpect(jsonPath("$.rating").value(4.5));
    }

    @Test
    public void testGetMovieReview() throws Exception {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setMovieReview("Test Review");

        when(reviewService.getMovieReview("Test Movie")).thenReturn(Collections.singletonList(reviewDto));

        mockMvc.perform(get("/movie/getMovieReview")
                .param("movieName", "Test Movie"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].movieReview").value("Test Review"));
    }

    @Test
    public void testAddReview() throws Exception {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setMovieReview("Test Review");

        Review review = new Review();
        review.setMovieReview("Test Review");

        when(reviewService.addReview(review, "Test Movie")).thenReturn(reviewDto);

        mockMvc.perform(post("/movie/addReview")
                .param("movieName", "Test Movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.movieReview").value("Test Review"));
    }

    @Test
    public void testGetMovieByGenre() throws Exception {
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Test Movie");
        movieDto.setRating(4.5);

        when(movieService.getMovieByGenre(Genre.ACTION)).thenReturn(Collections.singletonList(movieDto));

        mockMvc.perform(get("/movie/getMovieByGenre")
                .param("genre", "ACTION"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Movie"))
                .andExpect(jsonPath("$[0].rating").value(4.5));
    }
}
