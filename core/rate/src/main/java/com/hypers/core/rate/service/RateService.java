package com.hypers.core.rate.service;

import com.hypers.core.rate.model.Rate;
import com.hypers.core.rate.model.RateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RateService {

  @Autowired
  private RateRepository repo;

  @NewSpan
  @GetMapping("/rate/{movieId}")
  public Rate getRate(@PathVariable int movieId) {
    log.info("/rate/{} called", movieId);
    return repo.findOne(movieId);
  }

}
