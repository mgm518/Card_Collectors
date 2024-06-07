package org.markmcguire.cardcollectors.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
/**
 * This represents the typical user within the system.
 */
public class PlayerDTO {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;
  String username;
  String password;
  String email;

}
