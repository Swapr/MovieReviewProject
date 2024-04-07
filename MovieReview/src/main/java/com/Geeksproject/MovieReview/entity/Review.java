package com.Geeksproject.MovieReview.entity;

import com.Geeksproject.MovieReview.dto.ReviewDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Review {


	@Id
	@Column(name = "id" ,nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String movieReview;
	private double rating;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "movie_id",nullable = false)
	private Movie movie;

	public ReviewDto getReviewDto()
	{
		return ReviewDto.builder()
		         .id(this.id)
		         .movieReview(this.movieReview)
		         .rating(this.rating)
		         .build();                         //we will set movie name in service layer.that's why not declared here

	}
}
