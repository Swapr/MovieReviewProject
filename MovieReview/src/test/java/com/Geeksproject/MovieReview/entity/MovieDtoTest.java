package com.Geeksproject.MovieReview.entity;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.Geeksproject.MovieReview.dto.MovieDto;
import com.Geeksproject.MovieReview.dto.ReviewDto;
import com.Geeksproject.MovieReview.enums.Genre;



@SpringBootTest
public class MovieDtoTest {

    @Test
    public void testMovieDtoCreationWithDefaultValues() {
        MovieDto movieDto = new MovieDto();
        assertNull(movieDto.getId());
        assertNull(movieDto.getTitle());
        assertNull(movieDto.getGenre());
        assertNull(movieDto.getRating());
        assertNull(movieDto.getReview());
        assertNull(movieDto.getCreatedDate());
        assertNull(movieDto.getUpdatedDate());
    }

    @Test
    public void testMovieDtoCreationWithGivenValues() {
        Long id = 1L;
        String title = "The Shawshank Redemption";
        Genre genre = Genre.DRAMA;
        Double rating = 9.3;
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime updatedDate = LocalDateTime.now();
        List<ReviewDto> reviewDtos=new ArrayList<>();
        ReviewDto reviewDto=new ReviewDto(1L,"great movie",9.3,"The Shawshank Redemption");
        
        reviewDtos.add(reviewDto);

        MovieDto movieDto = new MovieDto(id, title, genre, rating,reviewDtos, createdDate, updatedDate);

        assertEquals(id, movieDto.getId());
        assertEquals(title, movieDto.getTitle());
        assertEquals(genre, movieDto.getGenre());
        assertEquals(rating, movieDto.getRating(), 0.01);
        assertNotNull(movieDto.getReview());
        assertEquals(createdDate, movieDto.getCreatedDate());
        assertEquals(updatedDate, movieDto.getUpdatedDate());
    }

    @Test
    public void testMovieDtoBuildUsingBuilderPattern() {
        Long id = 2L;
        String title = "The Godfather";
        Genre genre = Genre.ACTION;
        Double rating = 9.2;
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime updatedDate = LocalDateTime.now();
        List<ReviewDto> reviewDtos=new ArrayList<>();
        ReviewDto reviewDto=new ReviewDto(1L,"great movie",9.3,"The Shawshank Redemption");
        
        reviewDtos.add(reviewDto);

        MovieDto movieDto = MovieDto.builder()
                .id(id)
                .title(title)
                .genre(genre)
               .rating(rating)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .build();
        movieDto.setReview(reviewDtos);

        assertEquals(id, movieDto.getId());
        assertEquals(title, movieDto.getTitle());
        assertEquals(genre, movieDto.getGenre());
        assertEquals(rating, movieDto.getRating(), 0.01);
        assertNotNull(movieDto.getReview());
        assertEquals(createdDate, movieDto.getCreatedDate());
        assertEquals(updatedDate, movieDto.getUpdatedDate());
        assertNotNull(movieDto.getReview());
    }

    @Test
    public void testMovieDtoGetterAndSetterMethods() {
        Long id = 3L;
        String title = "The Dark Knight";
        Genre genre = Genre.ACTION;
        Double rating = 9.0;
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime updatedDate = LocalDateTime.now();
        List<ReviewDto> reviewDtos=new ArrayList<>();
        ReviewDto reviewDto=new ReviewDto(1L,"great movie",9.3,"The Shawshank Redemption");
        
        reviewDtos.add(reviewDto);

        MovieDto movieDto = new MovieDto();
        movieDto.setReview(reviewDtos);

        movieDto.setId(id);
        movieDto.setTitle(title);
        movieDto.setGenre(genre);
        movieDto.setRating(rating);
        movieDto.setCreatedDate(createdDate);
        movieDto.setUpdatedDate(updatedDate);

        assertEquals(id, movieDto.getId());
        assertEquals(title, movieDto.getTitle());
        assertEquals(genre, movieDto.getGenre());
        assertEquals(rating, movieDto.getRating(), 0.01);
        assertNotNull(movieDto.getReview());
        assertEquals(createdDate, movieDto.getCreatedDate());
        assertEquals(updatedDate, movieDto.getUpdatedDate());
    }

    @Test
    public void testMovieDtoToStringMethod() {
        Long id = 3L;
        String title = "The Dark Knight";
        Genre genre = Genre.ACTION;
        Double rating = 9.0;
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime updatedDate = LocalDateTime.now();
        List<ReviewDto> reviewDtos=new ArrayList<>();
        ReviewDto reviewDto=new ReviewDto(1L,"great movie",9.3,"The Shawshank Redemption");
        
        reviewDtos.add(reviewDto);

        MovieDto movieDto = new MovieDto(id, title, genre, rating,reviewDtos, createdDate, updatedDate);

        String toString = movieDto.toString();

        assertTrue(toString.contains("MovieDto"));
        assertTrue(toString.contains("id=" + id));
        assertTrue(toString.contains("title=" + title));
        assertTrue(toString.contains("genre=" + genre));
        assertTrue(toString.contains("rating=" + rating));
        assertTrue(toString.contains("createdDate=" + createdDate));
        assertTrue(toString.contains("updatedDate=" + updatedDate));
    }
}