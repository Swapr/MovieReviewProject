package com.Geeksproject.MovieReview.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.Geeksproject.MovieReview.dto.MovieDto;
import com.Geeksproject.MovieReview.dto.ReviewDto;
import com.Geeksproject.MovieReview.enums.Genre;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Movie implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Enumerated(EnumType.STRING)
	private Genre genre;

	private Double rating;     //average of all ratings

	@OneToMany(mappedBy = "movie")
	private List<Review> review;

	@CreationTimestamp
	private Date createdDate;

	@UpdateTimestamp
	private Date updatedDate;

	 public MovieDto getMovieDtoresponse()
	    {
	    	return MovieDto.builder()
	    			       .title(this.title)
	    			       .genre(this.genre)
	    			       .rating(this.rating)
	    			       .build();
	    }
	 public List<ReviewDto> getReviewDto()
	 {
		 ArrayList<ReviewDto> reviewsDto=new ArrayList<>();
		 for (Review review2 : review) {
			 reviewsDto.add(review2.getReviewDto());
		}
		 return reviewsDto;
	 }

}
