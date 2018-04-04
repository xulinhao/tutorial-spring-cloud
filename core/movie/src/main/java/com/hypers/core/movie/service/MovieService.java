package com.hypers.core.movie.service;

import com.hypers.core.movie.model.Movie;
import com.hypers.core.movie.model.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieService {

  @Autowired
  private MovieRepository repo;

  @GetMapping("/movie/{id}")
  public Movie getMovie(@PathVariable int id) {
    return repo.findOne(id);
  }

}
