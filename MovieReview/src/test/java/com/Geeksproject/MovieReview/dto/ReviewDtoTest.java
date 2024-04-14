package com.Geeksproject.MovieReview.dto;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReviewDtoTest {

    @Test
    public void testReviewDtoGettersAndSetters() {
        Long id = 1L;
        String movieReview = "This movie is great!";
        double rating = 4.5;
        String title = "KFG";

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(id);
        reviewDto.setMovieReview(movieReview);
        reviewDto.setRating(rating);
        reviewDto.setTitle(title);

        assertEquals(id, reviewDto.getId());
        assertEquals(movieReview, reviewDto.getMovieReview());
        assertEquals(rating, reviewDto.getRating());
        assertEquals(title, reviewDto.getTitle());
    }
}

