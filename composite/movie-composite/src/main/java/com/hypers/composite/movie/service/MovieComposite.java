package com.hypers.composite.movie.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hypers.core.movie.model.Movie;
import com.hypers.core.cast.model.Cast;
import com.hypers.core.rate.model.Rate;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

// TODO
// 1. Feign
@Slf4j
@Component
public class MovieComposite {

  private RestTemplate rt = new RestTemplate();

  public ResponseEntity<Movie> getMovie(int movieId) throws IOException {
    URI uri = this.getServiceUrl("movie",
        "http://localhost:8081/movie");

    String url = uri.toString() + "/movie/" + movieId;
    log.info("Get movie from URL: {}", url);

    ResponseEntity<String> resultStr = rt.getForEntity(url, String.class);
    log.info("Get movie http-status: {}", resultStr.getStatusCode());
    log.info("Get movie body: {}", resultStr.getBody());

    return ResponseEntity.ok(readMovie(resultStr));
  }

  public ResponseEntity<List<Cast>> getCast(int movieId) throws IOException {
    URI uri = this.getServiceUrl("cast",
        "http://localhost:8081/cast");

    String url = uri.toString() + "/cast?movieId=" + movieId;
    log.info("Get casts from URL: {}", url);

    ResponseEntity<String> resultStr = rt.getForEntity(url, String.class);
    log.info("Get casts http-status: {}", resultStr.getStatusCode());
    log.info("Get casts body: {}", resultStr.getBody());

    return ResponseEntity.ok(readCast(resultStr));
  }

  public ResponseEntity<Rate> getRate(int movieId) throws IOException {
    URI uri = this.getServiceUrl("rate",
        "http://localhost:8081/rate");

    String url = uri.toString() + "/rate?movieId=" + movieId;
    log.info("Get rate from URL: {}", url);

    ResponseEntity<String> resultStr = rt.getForEntity(url, String.class);
    log.info("Get rate http-status: {}", resultStr.getStatusCode());
    log.info("Get rate body: {}", resultStr.getBody());

    return ResponseEntity.ok(readRate(resultStr));
  }

  // -------------- //
  // util functions //
  // -------------- //

  @Autowired
  private LoadBalancerClient loadBalancer;

  private URI getServiceUrl(String serviceId, String fallbackUri) {
    URI uri = null;
    try {
      ServiceInstance instance = loadBalancer.choose(serviceId);
      uri = instance.getUri();
      log.info("Resolved serviceId '{}' to URL '{}'.", serviceId, uri);

    } catch (RuntimeException e) {
      uri = URI.create(fallbackUri);
      log.warn("Failed to resolve serviceId '{}'. Fallback to URL '{}'.",
          serviceId, uri);
    }

    return uri;
  }

  private Movie readMovie(ResponseEntity<String> response) throws IOException {
    return new ObjectMapper().readValue(response.getBody(), Movie.class);
  }

  private List<Cast> readCast(ResponseEntity<String> response) throws
      IOException {
    return new ObjectMapper().readValue(response.getBody(),
        new TypeReference<List<Cast>>() {
        });
  }

  private Rate readRate(ResponseEntity<String> response) throws IOException {
    return new ObjectMapper().readValue(response.getBody(), Rate.class);
  }

}
