package com.hypers.composite.movie.service;

import com.hypers.composite.movie.model.ComposedMovie;
import com.hypers.core.cast.model.Cast;
import com.hypers.core.movie.model.Movie;
import com.hypers.core.rate.model.Rate;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MovieCompositeService {

  @Autowired
  private MovieComposite composite;

  @NewSpan
  @GetMapping("/movie/{movieId}")
  public ResponseEntity<ComposedMovie> getMovie(@PathVariable int movieId)
      throws IOException {

    // 1.1 If a movie is not returned successfully
    ResponseEntity<Movie> movieResult = composite.getMovie(movieId);
    if (!movieResult.getStatusCode().is2xxSuccessful()) {
      return ResponseEntity.status(movieResult.getStatusCode()).body(null);
    }

    // 1.2. If a movie is returned
    Movie movie = movieResult.getBody();

    // 2. Get optional cast
    List<Cast> casts = null;
    ResponseEntity<List<Cast>> castResult = composite.getCast(movieId);
    if (!castResult.getStatusCode().is2xxSuccessful()) {
      log.error("Failed to call getCast: {}", castResult.getStatusCode());
    } else {
      casts = castResult.getBody();
    }

    // 3. Get optional rate
    Rate rate = null;
    ResponseEntity<Rate> rateResult = composite.getRate(movieId);
    if (!rateResult.getStatusCode().is2xxSuccessful()) {
      log.error("Failed to call getRate: {}", rateResult.getStatusCode());
    } else {
      rate = rateResult.getBody();
    }

    return ResponseEntity.ok(new ComposedMovie(movie, casts, rate));
  }

}
