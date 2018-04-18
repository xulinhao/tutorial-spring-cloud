package com.hypers.core.rate.service;

import com.hypers.core.rate.model.Rate;
import com.hypers.core.rate.model.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateService {

  @Autowired
  private RateRepository repo;

  @GetMapping("/rate")
  public Rate getRate(
      @RequestParam(value = "movieId", required = true) int movieId) {
    return repo.findOne(movieId);
  }

}
