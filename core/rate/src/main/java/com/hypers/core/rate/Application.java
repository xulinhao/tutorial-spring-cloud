package com.hypers.core.rate;

import com.hypers.core.rate.model.Rate;
import com.hypers.core.rate.model.RateRepository;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class Application {

  @Autowired
  private RateRepository repo;

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class).web(true).run(args);
  }

  @Bean
  InitializingBean init() {
    return () -> {
      repo.save(new Rate(1, 1, 9.6f));
      repo.save(new Rate(2, 2, 9.5f));
      repo.save(new Rate(3, 3, 9.4f));
      repo.save(new Rate(4, 4, 9.4f));
      repo.save(new Rate(5, 5, 9.5f));
    };
  }

}

