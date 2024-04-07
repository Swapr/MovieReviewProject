package com.Geeksproject.MovieReview.service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Geeksproject.MovieReview.dto.MovieDto;
import com.Geeksproject.MovieReview.entity.Movie;
import com.Geeksproject.MovieReview.enums.Genre;
import com.Geeksproject.MovieReview.repo.MovieRepo;
@Service
public class MovieService {

	@Autowired
	private MovieRepo movieRepo;



	public MovieDto getMovie(String title)
	{

		Movie movie = movieRepo.findBytitleIgnoreCase(title);
		if(movie!=null)
		{
		return movie.getMovieDtoresponse();
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
		List<Movie> movieList = movieRepo.findAllByGenre(genre);
		List<MovieDto> movieDtos=new ArrayList<>();
		if(!movieList.isEmpty())
		{

			for (Movie movie : movieList) {
				movieDtos.add(movie.getMovieDtoresponse());

			}
		}

		List<MovieDto> sortedMovieDtos = movieDtos.stream()
		         .sorted(Comparator.comparing(MovieDto::getRating).reversed())
		         .collect(Collectors.toList());

		if(sortedMovieDtos.size()>5)
		{
			return movieDtos.subList(0, 4);
		}else

		return movieDtos;

	}




}
