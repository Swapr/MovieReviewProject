package com.Geeksproject.MovieReview.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Geeksproject.MovieReview.entity.Movie;
import com.Geeksproject.MovieReview.enums.Genre;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Long>  {

	public Movie findBytitleIgnoreCase(String title);
	public Boolean existsBytitleIgnoreCase(String title);
	public List<Movie> findAllByGenre(Genre genre);

}


