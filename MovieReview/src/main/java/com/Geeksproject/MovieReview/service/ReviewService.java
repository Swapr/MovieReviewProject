package com.Geeksproject.MovieReview.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.Geeksproject.MovieReview.dto.ReviewDto;
import com.Geeksproject.MovieReview.entity.Movie;
import com.Geeksproject.MovieReview.entity.Review;
import com.Geeksproject.MovieReview.repo.MovieRepo;
import com.Geeksproject.MovieReview.repo.ReviewRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepo reviewRepo;
	@Autowired
	private MovieRepo movieRepo;

	@Autowired
    private RedisTemplate<String,String> redisTemplate;
	
	public ReviewDto addReview(Review review,String movieName)            
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
			try {
				movieRepo.save(movie1);
				System.out.println("updated average vlaue of rating saved in movie db as rating="+rating);
				return reviewDto;
			}
			catch (Exception e) {
				System.out.println("saved review but unable to save updated rating in  movie");
				return reviewDto;
			}
			
			
		}
		else
			return null;

	}
	
	
	public List<ReviewDto> getMovieReview(String movieName)        // get Review List for movie name
	{
		System.out.println("Request come for this movie-"+movieName);
		String reviewDtosFromCache=null;
		try {
			 reviewDtosFromCache =redisTemplate.opsForValue().get(movieName);            //getting from cache if present
		}catch (RedisConnectionFailureException e) {
			System.out.println("redis is not available");
		}
		
		if(reviewDtosFromCache!=null)
		{
			System.out.println("cache hit");
			List<ReviewDto> convertStringToList = convertStringToList(reviewDtosFromCache);
			return convertStringToList;
		}
		if(movieRepo.existsBytitleIgnoreCase(movieName))
		{
		Movie movie = movieRepo.findBytitleIgnoreCase(movieName);
		List<Review> reviewList = reviewRepo.findAllByMovieId(movie.getId());

		List<ReviewDto> reviewDtos = reviewList.stream()
		          .map(review->{
		        	  ReviewDto reviewDto=review.getReviewDto();
		        	  reviewDto.setTitle(movieName);
		        	  return reviewDto;
		          })
		          .collect(Collectors.toList());

		String stringobject=convertListToString(reviewDtos);
		try {
		      redisTemplate.opsForValue().set(movieName, stringobject, 60, TimeUnit.SECONDS);     //setting in cache
		}catch (RedisConnectionFailureException e) {
			
		}
		return reviewDtos;
		}
		else
		return null;
	}

	
	
	
	
	
	
	
	
	
	
    private static String convertListToString(List<ReviewDto> objectList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(objectList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to convert string to list of objects
    private static List<ReviewDto> convertStringToList(String jsonString) {

        try {
        	String string2 = jsonString.substring(1, jsonString.length()-1);
        	ObjectMapper objectMapper = new ObjectMapper();
            String[] ReviewString = string2.split("\\},\\{"); // Split individual movie objects
            List<ReviewDto> reviews = new ArrayList<>();
            for (String movieStr : ReviewString) {
                // Add curly braces to each movie object
                if (!movieStr.startsWith("{")) {
                    movieStr = "{" + movieStr;
                }
                if (!movieStr.endsWith("}")) {
                    movieStr = movieStr + "}";
                }
                // Map JSON string to MovieDto object
                 ReviewDto reviewDto = objectMapper.readValue(movieStr, ReviewDto.class);
                reviews.add(reviewDto);
            }
            return reviews;
           
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        
        
        
    }
    
}


