package com.Geeksproject.MovieReview.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.Geeksproject.MovieReview.enums.Genre;

@SpringBootTest
public class MovieDtoTest {

    @Test
    public void testMovieDtoGettersAndSetters() {

        Long id = 1L;
        String title = "KFG";
        Genre genre = Genre.ACTION;
        Double rating = 4.5;
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime updatedDate = LocalDateTime.now();

        List<ReviewDto> reviewDtos = new ArrayList<>();
        ReviewDto reviewDto1 = new ReviewDto();

        MovieDto movieDto = new MovieDto();
    
        movieDto.setId(id);
        movieDto.setTitle(title);
        movieDto.setGenre(genre);
        movieDto.setRating(rating);
        movieDto.setReview(reviewDtos);
        movieDto.setCreatedDate(createdDate);
        movieDto.setUpdatedDate(updatedDate);

        assertEquals(id, movieDto.getId());
        assertEquals(title, movieDto.getTitle());
        assertEquals(genre, movieDto.getGenre());
        assertEquals(rating, movieDto.getRating());
        assertEquals(reviewDtos, movieDto.getReview());
        assertEquals(createdDate, movieDto.getCreatedDate());
        assertEquals(updatedDate, movieDto.getUpdatedDate());
    }
}
