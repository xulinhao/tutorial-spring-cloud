package com.hypers.core.cast.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Cast {

  @Getter
  @Setter
  @NonNull
  private int id;

  @Getter
  @Setter
  @NonNull
  private int movieId;

  @Getter
  @Setter
  @NonNull
  private String cast;

}
