package com.Geeksproject.MovieReview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MovieReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieReviewApplication.class, args);
	}

}
