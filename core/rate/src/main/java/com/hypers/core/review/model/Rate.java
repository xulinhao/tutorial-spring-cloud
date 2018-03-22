package com.hypers.core.review.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Rate {

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
  private float rate;

}
