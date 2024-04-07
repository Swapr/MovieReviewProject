package com.Geeksproject.MovieReview.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReviewDto {

	private Long id;

	private String movieReview;
	private double rating;

	private String title;



}
