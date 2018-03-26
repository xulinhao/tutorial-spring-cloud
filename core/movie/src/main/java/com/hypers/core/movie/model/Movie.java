package com.hypers.core.movie.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Movie {

  @Id
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
