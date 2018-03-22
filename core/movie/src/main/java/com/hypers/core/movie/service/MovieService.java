package com.hypers.core.movie.service;

import com.hypers.core.movie.model.Movie;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieService {

  @GetMapping("/movie/{id}")
  public Movie getMovie(@PathVariable int id) {
    return new Movie(id, "The Shawshank Redemption", 1994, "Frank Darabont");
  }

}
