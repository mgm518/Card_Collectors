package org.markmcguire.cardcollectors.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Rarity {
  SSR("SSR", 5),
  SR("SR", 15),
  R("R", 100);
  private final String value;
  private final int weight;
}
