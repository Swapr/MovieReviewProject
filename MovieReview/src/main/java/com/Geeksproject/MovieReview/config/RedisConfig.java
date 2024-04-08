package com.Geeksproject.MovieReview.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.Geeksproject.MovieReview.dto.MovieDto;
import com.Geeksproject.MovieReview.dto.ReviewDto;

@Configuration
public class RedisConfig {
	
	 @Bean
	    public RedisTemplate<String,Object> objectRedisTemplate (RedisConnectionFactory redisConnectionFactory){
	        RedisTemplate<String,Object> template = new RedisTemplate<>();
	        template.setConnectionFactory(redisConnectionFactory);
	        return template;
	    }
	@Bean
	public RedisTemplate<String,MovieDto> movieRedisTemplate(RedisConnectionFactory redisConnectionFactory)
	{
		 RedisTemplate<String, MovieDto> template = new RedisTemplate<>();
	        template.setConnectionFactory(redisConnectionFactory);
	        template.setKeySerializer(new StringRedisSerializer());
	        template.setValueSerializer(new Jackson2JsonRedisSerializer<MovieDto>(MovieDto.class));
	        return template;
	}
	
	@Bean
	public RedisTemplate<String,ArrayList<ReviewDto>> ReviewRedisTemplate(RedisConnectionFactory redisConnectionFactory)
	{
		 RedisTemplate<String, ArrayList<ReviewDto>> template = new RedisTemplate<>();
	        template.setConnectionFactory(redisConnectionFactory);
	        template.setKeySerializer(new StringRedisSerializer());
	        template.setValueSerializer(new Jackson2JsonRedisSerializer<ReviewDto>(ReviewDto.class));
	        return template;
	}
	
	@Bean
	public RedisTemplate<String,ArrayList<MovieDto>[]> ReviewRedisTemplate2(RedisConnectionFactory redisConnectionFactory)    //to deseriliaze array of list
	{  
		 RedisTemplate<String, ArrayList<MovieDto>[]> template = new RedisTemplate<>();
	        template.setConnectionFactory(redisConnectionFactory);
	        template.setKeySerializer(new StringRedisSerializer());
	        template.setValueSerializer(new Jackson2JsonRedisSerializer<MovieDto>(MovieDto.class));
	        return template;
	}
	
	@Bean
	public RedisTemplate<String,String> ReviewRedisTemplate1(RedisConnectionFactory redisConnectionFactory)  // to serialize list
	{
		 RedisTemplate<String, String> template = new RedisTemplate<>();
	        template.setConnectionFactory(redisConnectionFactory);
	        template.setKeySerializer(new StringRedisSerializer());
	        template.setValueSerializer(new Jackson2JsonRedisSerializer<MovieDto>(MovieDto.class));
	        return template;
	}
	
	
	@Bean
	@Primary
	public RedisTemplate<String, String> ReviewRedisTemplate3(RedisConnectionFactory redisConnectionFactory) {
	    RedisTemplate<String, String> template = new RedisTemplate<>();
	    template.setConnectionFactory(redisConnectionFactory);
	    template.setKeySerializer(new StringRedisSerializer());
	    template.setValueSerializer(new StringRedisSerializer());
	    return template;
	}
	
	


	
	
	
	
	

}
