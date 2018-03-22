package com.hypers.core.review.service;


import com.hypers.core.review.model.Rate;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RateService {

  @GetMapping("/rate")
  public Rate getRate(
      @RequestParam(value = "movieId", required = true) int movieId) throws
      Exception {

    int time = (int) Math.random() * 100;
    log.info("/rate called, processing time: {}", time);

    sleep(time);

    return new Rate(1, movieId, 9.3f);
  }

  private void sleep(int time) throws Exception {
    Thread.sleep(time);
  }

}
