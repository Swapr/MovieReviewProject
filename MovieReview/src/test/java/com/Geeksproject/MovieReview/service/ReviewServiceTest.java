package com.Geeksproject.MovieReview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import com.Geeksproject.MovieReview.dto.ReviewDto;
import com.Geeksproject.MovieReview.entity.Movie;
import com.Geeksproject.MovieReview.entity.Review;
import com.Geeksproject.MovieReview.enums.Genre;
import com.Geeksproject.MovieReview.repo.MovieRepo;
import com.Geeksproject.MovieReview.repo.ReviewRepo;

@SpringBootTest
public class ReviewServiceTest {

    @Mock
    private ReviewRepo reviewRepo;

    @Mock
    private MovieRepo movieRepo;

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddReview() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");
        movie.setRating(4.5);
        movie.setGenre(Genre.ACTION);

        Review review = new Review();
        review.setId(1L);
        review.setMovie(movie);
        review.setMovieReview("Excellent movie!");
        review.setRating(5.0);

        when(movieRepo.existsBytitleIgnoreCase("Test Movie")).thenReturn(true);
        when(movieRepo.findBytitleIgnoreCase("Test Movie")).thenReturn(movie);
        when(reviewRepo.save(review)).thenReturn(review);
        when(reviewRepo.getRatingAverage(1L)).thenReturn(4.8);

        ReviewDto reviewDto = reviewService.addReview(review, "Test Movie");

        assertNotNull(reviewDto);
        assertEquals(reviewDto.getTitle(), "Test Movie");
        assertEquals(reviewDto.getRating(), 5.0);
        assertEquals(reviewDto.getMovieReview(), "Excellent movie!");
    }

    @Test
    public void testAddReviewMovieNotFound() {
        Review review = new Review();
        review.setId(1L);
        review.setMovieReview("Excellent movie!");
        review.setRating(5.0);

        when(movieRepo.existsBytitleIgnoreCase("Test Movie")).thenReturn(false);

        ReviewDto reviewDto = reviewService.addReview(review, "Test Movie");

        assertNull(reviewDto);
    }

    @Test
    public void testGetMovieReview() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");
        movie.setRating(4.5);
        movie.setGenre(Genre.ACTION);

        Review review1 = new Review();
        review1.setId(1L);
        review1.setMovie(movie);
        review1.setMovieReview("Excellent movie!");
        review1.setRating(5.0);

        Review review2 = new Review();
        review2.setId(2L);
        review2.setMovie(movie);
        review2.setMovieReview("Good movie!");
        review2.setRating(4.0);

        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review1);
        reviewList.add(review2);

        when(movieRepo.findBytitleIgnoreCase("Test Movie")).thenReturn(movie);
        when(reviewRepo.findAllByMovieId(1L)).thenReturn(reviewList);
        when(movieRepo.existsBytitleIgnoreCase("Test Movie")).thenReturn(true);

        List<ReviewDto> reviewDtos = reviewService.getMovieReview("Test Movie");
        
        assertNotNull(reviewDtos);
        assertEquals(reviewDtos.size(), 2);
        assertEquals(reviewDtos.get(0).getMovieReview(), "Excellent movie!");
        assertEquals(reviewDtos.get(1).getMovieReview(), "Good movie!");
    }
}
