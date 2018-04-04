package com.hypers.api.service;

import java.net.URI;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * XXX
 *
 * Two ways to access REST API:
 * <p/>
 * - RestTemplate
 * <p/>
 * - Feign
 *
 * Feign is demonstrated here; while RestTemplate in movie-composite module
*/
// TODO
// 1. Feign
@Slf4j
@RestController
public class MovieApiService {

  private RestTemplate rt = new RestTemplate();

  @Autowired
  private LoadBalancerClient loadBalancer;

  @GetMapping("/{movieId}")
  public ResponseEntity<String> getCompositedMovie(@PathVariable int movieId) {

    URI uri = loadBalancer.choose("movie-composite").getUri();
    String url = uri.toString() + "/movie/" + movieId;

    log.info("Get composited movie from URL: {}", url);

    ResponseEntity<String> result = rt.getForEntity(url, String.class);

    log.info("Get composited movie http-status: {}", result.getStatusCode());
    log.info("Get composited movie body: {}", result.getBody());

    return result;
  }

}
