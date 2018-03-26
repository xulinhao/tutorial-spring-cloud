package com.hypers.core.cast.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Delayer {

  @Value("${delayer.defaultMinMs}")
  private int minMs;

  @Value("${delayer.defaultMaxMs}")
  private int maxMs;

  public void setProcessingTime(int minMs, int maxMs) {
    if (minMs < 0) {
      minMs = 0;
    }
    
    if (maxMs < minMs) {
      maxMs = minMs;
    }

    this.minMs = minMs;
    this.maxMs = maxMs;

    log.info("Set response time to {} - {} ms.", this.minMs, this.maxMs);
  }

  public int getProcessingTime() {
    int time = minMs + (int) (Math.random() * (maxMs - minMs));
    log.info("Return calculated processing time: {} ms", time);

    return time;
  }

}
