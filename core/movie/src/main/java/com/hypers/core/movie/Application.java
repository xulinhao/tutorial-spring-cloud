package com.hypers.core.movie;

import com.hypers.core.movie.model.Movie;
import com.hypers.core.movie.model.MovieRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class Application {

  @Autowired
  private MovieRepository repo;

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class).web(true).run(args);
  }

  @Bean
  InitializingBean init() {
    return () -> {
      repo.save(
          new Movie(1, "The Shawshank Redemption",
              1994, "Frank Darabont"));
      repo.save(
          new Movie(2, "霸王别姬",
              1993, "陈凯歌"));
      repo.save(
          new Movie(3, "Léon",
              1994, "Luc Besson"));
      repo.save(
          new Movie(4, "Forrest Gump",
              1994, "Robert Zemeckis"));
      repo.save(
          new Movie(5, "La vita è bella",
              1997, "Roberto Benigni"));
    };
  }

}
