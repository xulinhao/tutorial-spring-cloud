package com.hypers.composite.movie.service;

import com.hypers.core.movie.model.Movie;
import com.hypers.core.cast.model.Cast;
import com.hypers.core.rate.model.Rate;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class MovieComposite {

  private RestTemplate rt;

  public ResponseEntity<Movie> getMovie(int movieId) {
    return null;
  }

  public ResponseEntity<List<Cast>> getCast(int movieId) {
    return null;
  }

  public ResponseEntity<Rate> getRate(int movieId) {
    return null;
  }

}
