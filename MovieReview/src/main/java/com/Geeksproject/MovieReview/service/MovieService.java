package com.Geeksproject.MovieReview.service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
		
		MovieDto movieDto2 = redisTemplate.opsForValue().get(title+1);      //saving different key for this movie because same key is used for  getMovieReview which fetching wrong data from redis .
		if(movieDto2!=null)
		{
			return movieDto2;
		}
		Movie movie = movieRepo.findBytitleIgnoreCase(title);
		if(movie!=null)
		{
			MovieDto movieDtoresponse = movie.getMovieDtoresponse();                  
			List<ReviewDto> reviewDtos= movie.getReviewDto();                                       
			movieDtoresponse.setReview(reviewDtos);                                            // setting Review dto in movieDto for parsing .
			redisTemplate.opsForValue().set(title+1, movieDtoresponse, 10, TimeUnit.SECONDS);    //saving in cache
			
		return movieDtoresponse;
		}
		else
			return null;
	}
	
	

	public Movie addMovie(Movie movie)
	{
		Movie movie1 = movieRepo.save(movie);
		return movie1;

	}
	
	
	
	

	
	public List<MovieDto> getMovieByGenre(Genre genre)      // we will send only to 5 rated movies for specefic genre
	{

		String string = redisTemplate3.opsForValue().get(genre.toString());         //used storing list as string in redis and then getting as string and conveting string to list
		System.out.println(string);
		if(string!=null)
		{
			String string2 = string.substring(1, string.length()-1);
			List<MovieDto> list = convertStringToList(string2);
			System.out.println("cache hit");
			return list;
		}
		List<Movie> movieList = movieRepo.findAllByGenre(genre);
		List<MovieDto> movieDtos=new ArrayList<>();
		if(!movieList.isEmpty())
		{

			for (Movie movie : movieList) {
				MovieDto movieDto = movie.getMovieDtoresponse();
				movieDto.setReview(movie.getReviewDto());
				movieDtos.add(movieDto);
				
			}
		}

		List<MovieDto> sortedMovieDtos = movieDtos.stream()
		         .sorted(Comparator.comparing(MovieDto::getRating).reversed())
		         .collect(Collectors.toList());


		if(sortedMovieDtos.size()>5)
		{
			List<MovieDto> movieDtos2=sortedMovieDtos.subList(0, 4);
			String convertListToString2=convertListToString(movieDtos2);
			redisTemplate3.opsForValue().set(genre.toString(),convertListToString2 , 60, TimeUnit.SECONDS);
		}
			String convertListToString = convertListToString(sortedMovieDtos);
		redisTemplate3.opsForValue().set(genre.toString(),convertListToString , 60, TimeUnit.SECONDS);
		return movieDtos;

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
