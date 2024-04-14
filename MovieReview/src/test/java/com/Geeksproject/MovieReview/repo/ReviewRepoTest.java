package com.Geeksproject.MovieReview.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.Geeksproject.MovieReview.entity.Movie;
import com.Geeksproject.MovieReview.entity.Review;
import com.Geeksproject.MovieReview.enums.Genre;

@DataJpaTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReviewRepoTest {

    @Autowired
    private ReviewRepo reviewRepo;
    @Autowired
    private MovieRepo movieRepo;

    @BeforeEach
    public void setUp() {
    	
    	movieRepo.deleteAll();
    	reviewRepo.deleteAll();
        Movie movie1 = Movie.builder()
        		.id(1L)
                .title("KFG")
                .genre(Genre.ACTION)
                .build();
        movieRepo.save(movie1);

        Movie movie2 = Movie.builder()
                .id(2L)
        		.title("KGF")
                .genre(Genre.ACTION)
                .build();
        movieRepo.save(movie2);
        
           }

    @Test
    public void testFindAllByMovieId() {
        Optional<Movie> findById = movieRepo.findById(1L);
        Movie movie1 = findById.get();

        Review review = new Review();
        review.setId(1L);
        review.setRating(4.5);
        review.setMovie(movie1);
        reviewRepo.save(review);

        List<Review> reviews = reviewRepo.findAllByMovieId(1L);

        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals(4.5, reviews.get(0).getRating());
    }

    @Test
    public void testGetRatingAverage() {
        Optional<Movie> findById1 = movieRepo.findById(1L);
        Movie movie1 = findById1.get();

        Optional<Movie> findById2 = movieRepo.findById(2L);
        Movie movie2 = findById2.get();

        Review review1 = new Review();
        review1.setId(1L);
        review1.setRating(4.0);
        review1.setMovie(movie1);
        reviewRepo.save(review1);

        Review review2 = new Review();
        review2.setId(2L);
        review2.setRating(3.5);
        review2.setMovie(movie2);
        reviewRepo.save(review2);
        
        Review review3 = new Review();
        review3.setId(3L);
        review3.setRating(4);
        review3.setMovie(movie2);
        reviewRepo.save(review3);

      
        Double averageRating = reviewRepo.getRatingAverage(2L);

        assertNotNull(averageRating);
        assertEquals(3.75, averageRating);
    }
}