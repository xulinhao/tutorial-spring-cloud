package com.hypers.core.cast.service;

import com.hypers.core.cast.model.Cast;
import com.hypers.core.cast.model.CastRepository;
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
  private Delayer delayer;

  @Autowired
  private CastRepository repo;

  @GetMapping("/cast")
  public List<Cast> getCast(
      @RequestParam(value = "movieId", required = true) int movieId) throws
      Exception {

    int time = delayer.getProcessingTime();
    log.info("/cast called, processing time: {}", time);

    sleep(time);

    List<Cast> result = repo.findByMovieId(movieId);

    log.info("/cast response size: {}", result.size());

    return result;
  }

  private void sleep(int pt) throws Exception {
    Thread.sleep(pt);
  }

}
