package com.Geeksproject.MovieReview.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Geeksproject.MovieReview.dto.ReviewDto;
import com.Geeksproject.MovieReview.entity.Movie;
import com.Geeksproject.MovieReview.entity.Review;
import com.Geeksproject.MovieReview.repo.MovieRepo;
import com.Geeksproject.MovieReview.repo.ReviewRepo;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepo reviewRepo;
	@Autowired
	private MovieRepo movieRepo;

//	public List<ReviewDto> getMovieReview(Movie movie)
//	{
//		if(movie==null)
//		{
//			return null;
//		}
//		System.out.println("this is movie coming for reivew request "+movie.getTitle());
//		String movieName=movie.getTitle();
//		Movie movie2 = movieRepo.findBytitleIgnoreCase(movieName);
//		List<Review> reviewList = reviewRepo.findAllByMovieId(movie2.getId());
//		List<ReviewDto> reviewDtos=new ArrayList<>();
//		for (Review review : reviewList) {
//			reviewDtos.add(review.getReviewDto());
//		}
//		return reviewDtos;
//	}

	public ReviewDto addReview(Review review,String movieName)            //add review
	{

		if(movieRepo.existsBytitleIgnoreCase(movieName))
		{
			Movie movie1=movieRepo.findBytitleIgnoreCase(movieName);
			review.setMovie(movie1);
			Review savedReview = reviewRepo.save(review);
			System.out.println("this movie saved-"+movie1.getTitle());
			ReviewDto reviewDto= ReviewDto.builder()
			                              .id(savedReview.getId())
			                              .movieReview(savedReview.getMovieReview())
			                              .rating(savedReview.getRating())
			                              .title(movieName)
			                              .build();

			System.out.println("Review Dto build success");
			Double rating=reviewRepo.getRatingAverage(movie1.getId());                             // to get avg of rating and update in table
			DecimalFormat decimalFormat=new DecimalFormat("#0.000");
			rating=Double.parseDouble((decimalFormat.format(rating)));                             // it will make double to string for precsion of 3 digits after decimal point and then again that string is converted to doulbw t ostore in db
			movie1.setRating(rating);
			movieRepo.save(movie1);
			System.out.println("updated average vlaue of rating saved in movie db as rating="+rating);
			return reviewDto;
		}
		else
			return null;

	}


	public List<ReviewDto> getMovieReview(String movieName)        // get Review List for movie name
	{
		System.out.println("Request come for this movie-"+movieName);
		if(movieRepo.existsBytitleIgnoreCase(movieName))
		{
		Movie movie = movieRepo.findBytitleIgnoreCase(movieName);
		List<Review> reviewList = reviewRepo.findAllByMovieId(movie.getId());
		List<ReviewDto> reviewDtos=new ArrayList<>();
		for (Review review : reviewList) {
			ReviewDto reviewDto=review.getReviewDto();
			reviewDto.setTitle(movieName);
			reviewDtos.add(reviewDto);

		  }
		return reviewDtos;
		}
		else
		return null;
	}




}
