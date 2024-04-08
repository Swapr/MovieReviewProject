package com.Geeksproject.MovieReview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Geeksproject.MovieReview.entity.Movie;
import com.Geeksproject.MovieReview.service.MovieService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
    private	MovieService movieService;

	@PostMapping("/addmovie/movie")
	public ResponseEntity<Movie> addMovie(@RequestBody @Validated Movie movie)
	{
		Movie movie1 = movieService.addMovie(movie);
		if(movie1!=null)
		{
			System.out.println("new movie is aded");
			return ResponseEntity.status(HttpStatus.CREATED).body(movie1);
		}
		else

		return ResponseEntity.badRequest().body(null);
	}




}
