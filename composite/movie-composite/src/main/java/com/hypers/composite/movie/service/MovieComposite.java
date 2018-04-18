package com.hypers.composite.movie.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hypers.core.cast.model.Cast;
import com.hypers.core.movie.model.Movie;
import com.hypers.core.rate.model.Rate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class MovieComposite {

  private RestTemplate rt = new RestTemplate();

  // ------------------
  // Service Functions
  // ------------------

  @HystrixCommand(fallbackMethod = "defaultMovie")
  public ResponseEntity<Movie> getMovie(int movieId) throws IOException {
    URI uri = this.getServiceUrl("service.movie");

    String url = uri.toString() + "/movie/" + movieId;
    log.info("Get movie from URL: {}", url);

    ResponseEntity<String> resultStr = rt.getForEntity(url, String.class);
    log.info("Get movie http-status: {}", resultStr.getStatusCode());
    log.info("Get movie body: {}", resultStr.getBody());

    return ResponseEntity.ok(
        new ObjectMapper().readValue(resultStr.getBody(), Movie.class));
  }

  public ResponseEntity<Movie> defaultMovie(int movieId) {
    log.warn("Fallback getMovie");
    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
  }

  @HystrixCommand(fallbackMethod = "defaultCast")
  public ResponseEntity<List<Cast>> getCast(int movieId) throws IOException {
    URI uri = this.getServiceUrl("service.cast");

    String url = uri.toString() + "/cast?movieId=" + movieId;
    log.info("Get casts from URL: {}", url);

    ResponseEntity<String> resultStr = rt.getForEntity(url, String.class);
    log.info("Get casts http-status: {}", resultStr.getStatusCode());
    log.info("Get casts body: {}", resultStr.getBody());

    return ResponseEntity.ok(
        new ObjectMapper().readValue(resultStr.getBody(),
            new TypeReference<List<Cast>>() {
            }));
  }

  public ResponseEntity<List<Cast>> defaultCast(int movieId) {
    log.warn("Fallback getCast");
    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
  }

  @HystrixCommand(fallbackMethod = "defaultRate")
  public ResponseEntity<Rate> getRate(int movieId) throws IOException {
    URI uri = this.getServiceUrl("service.rate");

    String url = uri.toString() + "/rate?movieId=" + movieId;
    log.info("Get rate from URL: {}", url);

    ResponseEntity<String> resultStr = rt.getForEntity(url, String.class);
    log.info("Get rate http-status: {}", resultStr.getStatusCode());
    log.info("Get rate body: {}", resultStr.getBody());

    return ResponseEntity.ok(
        new ObjectMapper().readValue(resultStr.getBody(), Rate.class));
  }

  public ResponseEntity<Rate> defaultRate(int movieId) {
    log.warn("Fallback getRate");
    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
  }

  // ------------------
  // Util Function
  // ------------------

  @Autowired
  private LoadBalancerClient loadBalancer;

  private URI getServiceUrl(String serviceId) {
    ServiceInstance instance = loadBalancer.choose(serviceId);
    URI uri = instance.getUri();
    log.info("Resolved serviceId '{}' to URL '{}'.", serviceId, uri);

    return uri;
  }

}
