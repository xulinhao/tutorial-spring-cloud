package com.hypers.composite.movie.model;

import com.hypers.core.cast.model.Cast;
import com.hypers.core.movie.model.Movie;
import com.hypers.core.rate.model.Rate;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.ToString;

@ToString
public class ComposedMovie {

  @Getter
  private int movieId;

  @Getter
  private String name;

  @Getter
  private int year;

  @Getter
  private String director;

  @Getter
  private List<String> casts;

  @Getter
  private float rate;

  public ComposedMovie(Movie movie, List<Cast> casts, Rate rate) {
    // 1. Make movie
    this.movieId = movie.getId();
    this.name = movie.getName();
    this.year = movie.getYear();
    this.director = movie.getDirector();

    // 2. Copy casts
    if (casts != null) {
      this.casts = casts.stream().map(Cast::getCast)
          .collect(Collectors.toList());
    }

    // 3. Copy rate
    if (rate != null) {
      this.rate = rate.getRate();
    }
  }

}
