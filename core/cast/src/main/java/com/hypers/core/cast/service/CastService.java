package com.hypers.core.cast.service;

import com.hypers.core.cast.model.Cast;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CastService {

  @Autowired
  private ProcessingTime pt;

  @GetMapping("/cast")
  public List<Cast> getCast(
      @RequestParam(value = "movieId", required = true) int movieId) throws
      Exception {

    int time = pt.getProcessingTime();
    log.info("/cast called, processing time: {}", time);

    sleep(time);

    List<Cast> result = new ArrayList<>();
    result.add(new Cast(1, movieId, "Tim Robbins"));
    result.add(new Cast(2, movieId, "Morgan Freeman"));
    result.add(new Cast(3, movieId, "Bob Gunton"));

    log.info("/cast response size: {}", result.size());

    return result;
  }

  private void sleep(int pt) throws Exception {
    Thread.sleep(pt);
  }

}
