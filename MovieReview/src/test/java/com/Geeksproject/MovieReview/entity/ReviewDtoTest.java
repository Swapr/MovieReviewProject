package com.Geeksproject.MovieReview.entity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.Geeksproject.MovieReview.dto.ReviewDto;

@SpringBootTest
class ReviewDtoTest {

    @Test
    void testBuilder() {
        ReviewDto reviewDto = ReviewDto.builder()
               .id(1L)
               .movieReview("Great movie!")
               .rating(8.5)
               .title("Movie Title")
               .build();

        assertNotNull(reviewDto);
        assertEquals(1L, reviewDto.getId());
        assertEquals("Great movie!", reviewDto.getMovieReview());
        assertEquals(8.5, reviewDto.getRating());
        assertEquals("Movie Title", reviewDto.getTitle());
    }

    @Test
    void testSettersAndGetters() {
        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setId(1L);
        reviewDto.setMovieReview("Great movie!");
        reviewDto.setRating(8.5);
        reviewDto.setTitle("Movie Title");

        assertEquals(1L, reviewDto.getId());
        assertEquals("Great movie!", reviewDto.getMovieReview());
        assertEquals(8.5, reviewDto.getRating());
        assertEquals("Movie Title", reviewDto.getTitle());
    }
}