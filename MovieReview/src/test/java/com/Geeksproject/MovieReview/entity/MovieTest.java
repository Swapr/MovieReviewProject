package com.Geeksproject.MovieReview.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.Geeksproject.MovieReview.dto.MovieDto;
import com.Geeksproject.MovieReview.dto.ReviewDto;
import com.Geeksproject.MovieReview.enums.Genre;

@SpringBootTest
public class MovieTest {
	
	@Test
    public void testGetMovieDtoResponse() {
        // Given
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setGenre(Genre.ACTION);
        movie.setRating(4.5);

        // When
        MovieDto movieDto = movie.getMovieDtoresponse();

        // Then
        assertEquals("Test Movie", movieDto.getTitle());
        assertEquals(Genre.ACTION, movieDto.getGenre());
        assertEquals(4.5, movieDto.getRating());
    }
	
	 @Test
	    public void testGetReviewDto() {
	        // Given
	        Movie movie = new Movie();
	        List<Review> reviews = new ArrayList<>();
	        Review review1 = mock(Review.class);
	        Review review2 = mock(Review.class);
	        when(review1.getReviewDto()).thenReturn(new ReviewDto());
	        when(review2.getReviewDto()).thenReturn(new ReviewDto());
	        reviews.add(review1);
	        reviews.add(review2);
	        movie.setReview(reviews);

	        // When
	        List<ReviewDto> reviewDtos = movie.getReviewDto();

	        // Then
	        assertEquals(2, reviewDtos.size());
	    }
	 
	 @Test
	    public void testObjectCreation() {
	        // Given
	        String title = "Test Movie";
	        Genre genre = Genre.ACTION;
	        Double rating = 4.5;
	        List<Review> reviews = new ArrayList<>();
	        LocalDateTime createdDate = LocalDateTime.now();
	        LocalDateTime updatedDate = LocalDateTime.now();

	        // When
	        Movie movie = new Movie(1L, title, genre, rating, reviews, createdDate, updatedDate);

	        // Then
	        assertNotNull(movie);
	        assertEquals(1L, movie.getId());
	        assertEquals(title, movie.getTitle());
	        assertEquals(genre, movie.getGenre());
	        assertEquals(rating, movie.getRating());
	        assertEquals(reviews, movie.getReview());
	        assertEquals(createdDate, movie.getCreatedDate());
	        assertEquals(updatedDate, movie.getUpdatedDate());
	    }
	 
	    @Test
	    public void testGettersAndSetters() {
	        // Given
	        Movie movie = new Movie();
	        String title = "Test Movie";
	        Genre genre = Genre.ACTION;
	        Double rating = 4.5;
	        List<Review> reviews = new ArrayList<>();
	        LocalDateTime createdDate = LocalDateTime.now();
	        LocalDateTime updatedDate = LocalDateTime.now();

	        // When
	        movie.setId(1L);
	        movie.setTitle(title);
	        movie.setGenre(genre);
	        movie.setRating(rating);
	        movie.setReview(reviews);
	        movie.setCreatedDate(createdDate);
	        movie.setUpdatedDate(updatedDate);

	        // Then
	        assertEquals(1L, movie.getId());
	        assertEquals(title, movie.getTitle());
	        assertEquals(genre, movie.getGenre());
	        assertEquals(rating, movie.getRating());
	        assertEquals(reviews, movie.getReview());
	        assertEquals(createdDate, movie.getCreatedDate());
	        assertEquals(updatedDate, movie.getUpdatedDate());
	    }


}
