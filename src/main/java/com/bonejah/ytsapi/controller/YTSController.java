package com.bonejah.ytsapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bonejah.ytsapi.model.Movie;
import com.bonejah.ytsapi.service.MovieService;
import com.bonejah.ytsapi.utils.YTSUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 *
 * brunolima on Mar 11, 2021
 * 
 */

@RestController
@RequestMapping("yts-api")
@RequiredArgsConstructor
@Log4j2
public class YTSController {
	
	private final MovieService service;
	
	@GetMapping(path = "/movies")
	public ResponseEntity<Page<Movie>> list(Pageable pageable ) {
		return ResponseEntity.ok(service.findAll(pageable));
	}
	
	@GetMapping(path = "/movies/{id}")
	public ResponseEntity<Movie> findById(@PathVariable long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping(path = "/movies/title/{title}")
	public ResponseEntity<List<Movie>> findById(@PathVariable String title) {
		return ResponseEntity.ok(service.findByTitle(title));
	}

	@GetMapping(path = "/status")
	public ResponseEntity<String> viewStatusApi() {
		return ResponseEntity.ok("YTS API online ;)");
	}

	@GetMapping(path = "/admin/count-movie")
	public ResponseEntity<String> countMovie() throws IOException {
		ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(YTSUtils.URL_YTS, String.class);
		log.info("ResponseEntity" + responseEntity);
		log.info("ResponseEntity" + responseEntity.getBody());

		String movie_count = new ObjectMapper()
									.readTree(responseEntity.getBody())
									.path("data")
									.findPath("movie_count")
									.toPrettyString();
		
		return ResponseEntity.ok(movie_count);
	}
	
	@GetMapping(path = "/admin/load-torrents/{pageLimit}/page-limit")
	public ResponseEntity<String> loadTorrents(@PathVariable Integer pageLimit) throws JsonMappingException, JsonProcessingException {
		
		while(pageLimit > 0) {
			String url = String.format(YTSUtils.URL_YTS + "?page=%d", pageLimit);
			ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(url, String.class);
			
			JsonNode movies = new ObjectMapper()
									.readTree(responseEntity.getBody())
									.path("data")
									.findPath("movies");
			
			for (JsonNode movieNode : movies) {
				Movie movie = new ObjectMapper().readValue(movieNode.toString(), Movie.class);
				log.info("Saved movie >>> " + movie);
				
				service.save(movie);
			}
			
			pageLimit--;
		}
		
		return ResponseEntity.ok("Success!");
	}
	
}
