package com.hypers.core.cast.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CastRepository extends JpaRepository<Cast, Integer> {

  List<Cast> findByMovieId(int movieId);

}
