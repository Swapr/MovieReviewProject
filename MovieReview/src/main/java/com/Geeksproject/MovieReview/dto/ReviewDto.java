package com.Geeksproject.MovieReview.dto;



import java.io.Serializable;

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
public class ReviewDto implements Serializable{

	private Long id;

	private String movieReview;
	private double rating;

	private String title;



}
