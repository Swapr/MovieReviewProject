package com.Geeksproject.MovieReview.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Geeksproject.MovieReview.entity.Review;

public interface ReviewRepo extends JpaRepository<Review, Long> {

	public List<Review> findAllByMovieId(Long  id);

	@Query(value ="select avg(rating) from review where movie_id=?1" ,nativeQuery = true )
	public Double getRatingAverage(Long id);

}
