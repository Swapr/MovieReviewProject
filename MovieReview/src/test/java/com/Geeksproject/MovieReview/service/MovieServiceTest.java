package com.Geeksproject.MovieReview.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;

import com.Geeksproject.MovieReview.dto.MovieDto;
import com.Geeksproject.MovieReview.entity.Movie;
import com.Geeksproject.MovieReview.enums.Genre;
import com.Geeksproject.MovieReview.repo.MovieRepo;
import com.Geeksproject.MovieReview.service.MovieService;

@SpringBootTest
public class MovieServiceTest {

	@Mock
	private MovieRepo movieRepo;

	@InjectMocks
	private MovieService movieService;
	
    @Mock
    @Qualifier("movieRedisTemplate")
    private RedisTemplate<String, MovieDto> movieRedisTemplate;

    @Mock
    @Qualifier("ReviewRedisTemplate3")
    private RedisTemplate<String, String> reviewRedisTemplate3;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		
		
	}

	@Test
	public void testGetMovie() {
		Movie movie = new Movie();
		movie.setId(1L);
		movie.setTitle("Test Movie");
		movie.setRating(4.5);
		movie.setGenre(Genre.ACTION);

		when(movieRepo.findBytitleIgnoreCase("Test Movie")).thenReturn(movie);

		MovieDto movieDto = movieService.getMovie("Test Movie");

		assertNotNull(movieDto);
		assertEquals(movieDto.getTitle(), "Test Movie");
		assertEquals(movieDto.getRating(), 4.5);
		assertEquals(movieDto.getGenre(), Genre.ACTION);
	}

	@Test
	public void testGetMovieNotFound() {
		when(movieRepo.findBytitleIgnoreCase("Test Movie")).thenReturn(null);

		MovieDto movieDto = movieService.getMovie("Test Movie");

		assertNull(movieDto);
	}

	@Test
	public void testAddMovie() {
		Movie movie = new Movie();
		movie.setId(1L);
		movie.setTitle("Test Movie");
		movie.setRating(4.5);
		movie.setGenre(Genre.ACTION);

		when(movieRepo.existsBytitleIgnoreCase("Test Movie")).thenReturn(false);
		when(movieRepo.save(movie)).thenReturn(movie);

		Movie movie1 = movieService.addMovie(movie);

		assertNotNull(movie1);
		assertEquals(movie1.getTitle(), "Test Movie");
		assertEquals(movie1.getRating(), 4.5);
		assertEquals(movie1.getGenre(), Genre.ACTION);
	}

	@Test
	public void testAddMovieAlreadyExists() {
		Movie movie = new Movie();
		movie.setId(1L);
		movie.setTitle("Test Movie");
		movie.setRating(4.5);
		movie.setGenre(Genre.ACTION);

		when(movieRepo.existsBytitleIgnoreCase("Test Movie")).thenReturn(true);

		Movie movie1 = movieService.addMovie(movie);

		assertNull(movie1);
	}

	@Test
	public void testGetMovieByGenre() {
		Movie movie1 = new Movie();
		movie1.setId(1L);
		movie1.setTitle("Test Movie 1");
		movie1.setRating(4.5);
		movie1.setGenre(Genre.ACTION);

		Movie movie2 = new Movie();
		movie2.setId(2L);
		movie2.setTitle("Test Movie 2");
		movie2.setRating(4.8);
		movie2.setGenre(Genre.ACTION);

		List<Movie> movieList = new ArrayList<>();
		movieList.add(movie1);
		movieList.add(movie2);

		when(movieRepo.findAllByGenre(Genre.ACTION)).thenReturn(movieList);

		List<MovieDto> movieDtos = movieService.getMovieByGenre(Genre.ACTION);

		assertNotNull(movieDtos);
		assertEquals(movieDtos.size(), 2);
		assertEquals(movieDtos.get(0).getTitle(), "Test Movie 2");
		assertEquals(movieDtos.get(1).getTitle(), "Test Movie 1");
	}
}