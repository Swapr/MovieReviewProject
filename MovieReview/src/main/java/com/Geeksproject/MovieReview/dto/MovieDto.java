package com.Geeksproject.MovieReview.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.Geeksproject.MovieReview.entity.Review;
import com.Geeksproject.MovieReview.enums.Genre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto implements Serializable{

private Long id;

	private String title;


	private Genre genre;

	private Double rating;


	
	private List<ReviewDto> review;


	private Date createdDate;

	private Date updatedDate;

}
