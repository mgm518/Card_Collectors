package org.markmcguire.cardcollectors.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
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
public class Pack {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;
  String name;
  String description;
  Integer size;
  @OneToMany
  List<Card> pool;
  // TODO implement logic for featured cards
//  @OneToMany
//  List<Card> featureCards;
}
