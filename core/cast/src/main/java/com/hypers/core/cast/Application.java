package com.hypers.core.cast;

import com.hypers.core.cast.model.Cast;
import com.hypers.core.cast.model.CastRepository;

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
  private CastRepository repo;

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class).web(true).run(args);
  }

  @Bean
  InitializingBean init() {
    return () -> {
      repo.save(new Cast(1, 1, "Tim Robbins"));
      repo.save(new Cast(2, 1, "Morgan Freeman"));
      repo.save(new Cast(3, 1, "Bob Gunton"));
      repo.save(new Cast(4, 1, "William Sadler"));
      repo.save(new Cast(5, 1, "Clancy Brown"));

      repo.save(new Cast(6, 2, "张国荣"));
      repo.save(new Cast(7, 2, "张丰毅"));
      repo.save(new Cast(8, 2, "巩俐"));
      repo.save(new Cast(9, 2, "葛优"));

      repo.save(new Cast(10, 3, "Jean Reno"));
      repo.save(new Cast(11, 3, "Natalie Portman"));
      repo.save(new Cast(12, 3, "Gary Oldman"));
      repo.save(new Cast(13, 3, "Danny Aiello"));
      repo.save(new Cast(14, 3, "Peter Appel"));

      repo.save(new Cast(15, 4, "Tom Hanks"));
      repo.save(new Cast(16, 4, "Robin Wright"));
      repo.save(new Cast(17, 4, "Gary Sinise"));
      repo.save(new Cast(18, 4, "Mykelti Williamson"));

      repo.save(new Cast(19, 5, "Roberto Benigni"));
      repo.save(new Cast(20, 5, "Nicoletta Braschi"));
      repo.save(new Cast(21, 5, "Giorgio Cantarini"));
    };
  }

}
