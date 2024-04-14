package com.Geeksproject.MovieReview.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.Geeksproject.MovieReview.dto.ReviewDto;

@SpringBootTest
public class ReviewTest {

    @Test
    public void testReviewConstructor() {
        Long id = 1L;
        String movieReview = "This is a movie review.";
        double rating = 4.5;
        Movie movie = mock(Movie.class);

        Review review = new Review(id, movieReview, rating, movie);

        assertAll(() -> {
            assertEquals(id, review.getId());
            assertEquals(movieReview, review.getMovieReview());
            assertEquals(rating, review.getRating());
            assertEquals(movie, review.getMovie());
        });
    }

    @Test
    public void testGetReviewDto() {
        Long id = 1L;
        String movieReview = "This is a movie review.";
        double rating = 4.5;
        Movie movie = mock(Movie.class);

        Review review = new Review(id, movieReview, rating, movie);

        ReviewDto reviewDto = review.getReviewDto();

        assertAll(() -> {
            assertEquals(id, reviewDto.getId());
            assertEquals(movieReview, reviewDto.getMovieReview());
            assertEquals(rating, reviewDto.getRating());
        });
    }

    @Test
    public void testEqualsAndHashCode() {
        Long id = 1L;
        String movieReview = "This is a movie review.";
        double rating = 4.5;
        Movie movie = mock(Movie.class);

        Review review1 = new Review(id, movieReview, rating, movie);
        Review review2 = new Review(id, movieReview, rating, movie);

        assertAll(() -> {
            assertEquals(review1, review2);
            assertEquals(review1.hashCode(), review2.hashCode());
        });

        review2.setMovieReview("This is a different movie review.");

        assertNotEquals(review1, review2);

        review2.setMovieReview(movieReview);

        review2.setRating(3.5);

        assertNotEquals(review1, review2);

        review2.setRating(rating);

        review2.setMovie(null);

        assertNotEquals(review1, review2);

        review2.setMovie(movie);

        review2.setId(2L);

        assertNotEquals(review1, review2);
    }
}