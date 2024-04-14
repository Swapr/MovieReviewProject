package com.Geeksproject.MovieReview.service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import com.Geeksproject.MovieReview.dto.MovieDto;
import com.Geeksproject.MovieReview.dto.ReviewDto;
import com.Geeksproject.MovieReview.entity.Movie;
import com.Geeksproject.MovieReview.enums.Genre;
import com.Geeksproject.MovieReview.repo.MovieRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class MovieService {

	@Autowired
	private MovieRepo movieRepo;
 

	@Autowired
	private  RedisTemplate<String, MovieDto> redisTemplate;

	@Autowired
	@Qualifier("ReviewRedisTemplate3")
	private RedisTemplate<String, String> redisTemplate3;
	
	public MovieDto getMovie(String title)
	{
		MovieDto movieDto2=null;
		try {
			redisTemplate.opsForValue();                                //for checking wheter redis bean is injected ir not //for checking wheter redis bean is injected ir not 
			 movieDto2 = redisTemplate.opsForValue().get(title+1);     //saving different key for this movie because same key is used for  getMovieReview which fetching wrong data from redis .
				if(movieDto2!=null)
				{
					return movieDto2;
				}
		}catch (RedisConnectionFailureException e)
		{
			System.out.println("redis is not available");
		}
		catch(NullPointerException e)
		{
			System.out.println("redis bean is not available");
		}
		Movie movie = movieRepo.findBytitleIgnoreCase(title);
		if(movie!=null)
		{
			MovieDto movieDtoresponse = movie.getMovieDtoresponse();                  
			List<ReviewDto> reviewDtos= movie.getReviewDto();                                       
			movieDtoresponse.setReview(reviewDtos);                                            // setting Review dto in movieDto for parsing .
			try { 
				redisTemplate.opsForValue();                                              //for checking wheter redis bean is injected ir not 
				redisTemplate.opsForValue().set(title+1, movieDtoresponse, 10, TimeUnit.SECONDS);   	   //saving in cache
			}catch (Exception e) {
				
			}	
			
		    return movieDtoresponse;
		}
		else
			return null;
	}
	
	

	public Movie addMovie(Movie movie )
	{
		if(movieRepo.existsBytitleIgnoreCase(movie.getTitle()))
		{
			
			System.out.println("movie already present with same title");
			return null; 
		}
		Movie movie1 = movieRepo.save(movie);
		return movie1;

	}
	
	
	
	

	
	public List<MovieDto> getMovieByGenre(Genre genre)      // we will send only to 5 rated movies for specefic genre
	{

		try {
			redisTemplate.opsForValue();                                              //for checking wheter redis bean is injected ir not 
			String string = redisTemplate3.opsForValue().get(genre.toString());
			if(string!=null)
			{
				String string2 = string.substring(1, string.length()-1);
				List<MovieDto> list = convertStringToList(string2);
				System.out.println("cache hit");
				return list;
			}
		}
		catch (RedisConnectionFailureException e) {
		    // Handle the Redis connection failure exception here
			System.out.println("issue in redis server ");
		    
		}
		catch(NullPointerException e)
		{
			System.out.println("redis bean is not available");
		}
		         //used storing list as string in redis and then getting as string and conveting string to list
		
		List<Movie> movieList = movieRepo.findAllByGenre(genre);

		List<MovieDto> sortedMovieDtos=movieList.stream()
				                                .map(movie->{
				                                	MovieDto movieDtoresponse = movie.getMovieDtoresponse();
				                                	movieDtoresponse.setReview(movie.getReviewDto());
				                                	return movieDtoresponse;
				                                })
				                                .sorted(Comparator.comparing(MovieDto::getRating).reversed())
				                                .limit(5)
				                                .collect(Collectors.toList());
		

			String convertListToString = convertListToString(sortedMovieDtos);
			try {
				redisTemplate.opsForValue();                                              //for checking wheter redis bean is injected ir not 
				redisTemplate3.opsForValue().set(genre.toString(),convertListToString , 60, TimeUnit.SECONDS);
			}
			catch (RedisConnectionFailureException e) {
			    // Handle the Redis connection failure exception here
			    
			}
			catch(NullPointerException e)
			{
				System.out.println("redis bean is not available");
			}
		
		return sortedMovieDtos;

	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
    private static String convertListToString(List<MovieDto> objectList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(objectList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to convert string to list of objects
    private static List<MovieDto> convertStringToList(String jsonString) {

        try {
        	ObjectMapper objectMapper = new ObjectMapper();
            String[] movieStrings = jsonString.split("\\},\\{"); // Split individual movie objects
            List<MovieDto> movies = new ArrayList<>();
            for (String movieStr : movieStrings) {
                // Add curly braces to each movie object
                if (!movieStr.startsWith("{")) {
                    movieStr = "{" + movieStr;
                }
                if (!movieStr.endsWith("}")) {
                    movieStr = movieStr + "}";
                }
                // Map JSON string to MovieDto object
                MovieDto movie = objectMapper.readValue(movieStr, MovieDto.class);
                movies.add(movie);
            }
            return movies;
           
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        
        
        
    }
	


}
