package com.Geeksproject.MovieReview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Geeksproject.MovieReview.dto.MovieDto;
import com.Geeksproject.MovieReview.dto.ReviewDto;
import com.Geeksproject.MovieReview.entity.Review;
import com.Geeksproject.MovieReview.enums.Genre;
import com.Geeksproject.MovieReview.service.MovieService;
import com.Geeksproject.MovieReview.service.ReviewService;

@RestController
@RequestMapping("/movie")
public class MovieController {


	@Autowired
	private MovieService movieService;
	@Autowired
	private ReviewService reviewService;



	@GetMapping("/getMovie")
	public ResponseEntity<MovieDto> getMovie(@RequestParam String movieName)
	{
		MovieDto movie = movieService.getMovie(movieName);
		if(movie!=null)
		{
			System.out.println("movie details sent");
			return ResponseEntity.ok().body(movie);
		}
		else
			System.out.println("no matching movie found");
		return ResponseEntity.noContent().build();

	}


	@GetMapping("/getMovieReview")
	public ResponseEntity<List<ReviewDto>> getMovieReview(@RequestParam  String  movieName)
	{
		List<ReviewDto> reviewDtos= reviewService.getMovieReview(movieName);
		if(reviewDtos!=null)
		{
			System.out.println("review list is sent");
			return ResponseEntity.ok().body(reviewDtos);
		}
		else
			System.out.println("reviews not founf for given movie");
		return ResponseEntity.badRequest().body(null);
	}


	@PostMapping("/addReview")
	public ResponseEntity<ReviewDto> addReview(@RequestBody Review review ,@RequestParam String movieName)
	{
		System.out.println("this is recevied Review object "+review +"and movie name is="+movieName);
		ReviewDto addReview = reviewService.addReview(review,movieName);
		if(addReview!=null)
		{
			System.out.println("Review object send to user");
			return ResponseEntity.ok().body(addReview);
		}
		else
		return ResponseEntity.badRequest().body(null);
	}



	@GetMapping("/getMovieByGenre")
	public ResponseEntity<List<MovieDto>> getMovieByGenre(@RequestParam String genre)  // we will send only to 5 rated movies
	{
		System.out.println("coming request for this genre-"+genre);
		List<MovieDto> moviesByGenre = movieService.getMovieByGenre(Genre.valueOf(genre.toUpperCase()));
		if(moviesByGenre!=null)
		{
			System.out.println("movie list send to user");
			return ResponseEntity.ok().body(moviesByGenre);
		}
		else
			System.out.println("movieList not found ");
		return ResponseEntity.badRequest().body(null);
	}

}
