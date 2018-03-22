package com.hypers.core.movie.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Movie {

  @Getter
  @Setter
  @NonNull
  private int id;

  @Getter
  @Setter
  @NonNull
  private String name;

  @Getter
  @Setter
  @NonNull
  private int year;

  @Getter
  @Setter
  @NonNull
  private String director;

}
