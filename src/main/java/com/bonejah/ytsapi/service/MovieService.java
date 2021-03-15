package com.bonejah.ytsapi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bonejah.ytsapi.exception.BadRequestException;
import com.bonejah.ytsapi.model.Movie;
import com.bonejah.ytsapi.repository.MovieRepository;

import lombok.RequiredArgsConstructor;

/**
 *
 * brunolima on Mar 13, 2021
 * 
 */

@Service
@RequiredArgsConstructor
public class MovieService {

	private final MovieRepository repository;

	public Page<Movie> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public List<Movie> findByTitle(String title) {
		return repository.findByTitleStartingWith(title);
	}

	public Movie findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new BadRequestException("Movie with id:" + id + " not found."));
	}

	@Transactional(rollbackOn = Exception.class)
	public Movie save(Movie movie) {
		return repository.save(movie);
	}
//
//	@Transactional
//	public void delete(long id) {
//		repository.delete(findById(id));
//	}
//
//	@Transactional
//	public void update(MoviePutRequestBody moviePutRequestBody) {
//		Movie movieSaved = findById(moviePutRequestBody.getId());
//
//		Movie movie = MovieMapper.INSTANCE.toMovie(moviePutRequestBody);
//		movie.setId(movieSaved.getId());
//
//		repository.save(movie);
//	}

}
