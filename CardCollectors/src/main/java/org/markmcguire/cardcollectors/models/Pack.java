package org.markmcguire.cardcollectors.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
  @ManyToOne
  PackType type;
  @ManyToOne
  @JoinColumn(name = "player_id")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  Player user;
}
