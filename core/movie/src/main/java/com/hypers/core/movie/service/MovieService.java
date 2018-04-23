package com.hypers.core.movie.service;

import com.hypers.core.movie.model.Movie;
import com.hypers.core.movie.model.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MovieService {

  @Autowired
  private MovieRepository repo;

  @NewSpan
  @GetMapping("/movie/{id}")
  public Movie getMovie(@PathVariable int id) {
    log.info("/movie/{} called", id);
    return repo.findOne(id);
  }

}
