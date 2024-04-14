package com.Geeksproject.MovieReview.repo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.Geeksproject.MovieReview.entity.Movie;
import com.Geeksproject.MovieReview.enums.Genre;



@DataJpaTest
public class MovieRepoTest {

	

	@Autowired
	private MovieRepo movieRepo;
	
	
	
	 @BeforeEach
	    public void setup() {

	        Movie movie = Movie.builder()
	                .id(1l)
	                .title("KFG")
	                .genre(Genre.ACTION)
	                .build();

	        movieRepo.save(movie);
	        Movie movie2=Movie.builder()
	        		          .id(2L)
	        		          .title("KGF")
	        		          .genre(Genre.ACTION)
	        		          .build();
	       movieRepo.save(movie2);
	    }

	@Test
	public void testFindBytitleIgnoreCase() {


		Movie foundMovie = movieRepo.findBytitleIgnoreCase("KFG");
		assertNotNull(foundMovie);
		assertEquals("KFG", foundMovie.getTitle());
	}

	@Test
	public void testExistsBytitleIgnoreCase() {


		boolean exists = movieRepo.existsBytitleIgnoreCase("KFG");
		assertTrue(exists);
	}

	@Test
	public void testFindAllByGenre() {


		List<Movie> actionMovies = movieRepo.findAllByGenre(Genre.ACTION);
		assertFalse(actionMovies.isEmpty());
		assertEquals(2, actionMovies.size());
	}
}