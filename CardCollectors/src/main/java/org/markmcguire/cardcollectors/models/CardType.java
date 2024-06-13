package org.markmcguire.cardcollectors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardType {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;
  @NonNull
  @Column(nullable = false, unique = true)
  String name;
  @NonNull
  @Column(nullable = false)
  Rarity rarity;
}
