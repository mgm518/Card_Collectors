package org.markmcguire.cardcollectors.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PackType {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;
  String name;
  String description;
  @Builder.Default
  Integer size = 10;
  @Builder.Default
  Integer bonus = 0;
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  Set<CardType> pool;
  // TODO implement logic for featured cards
  //  @OneToMany
  //  List<Card> featureCards;
}
