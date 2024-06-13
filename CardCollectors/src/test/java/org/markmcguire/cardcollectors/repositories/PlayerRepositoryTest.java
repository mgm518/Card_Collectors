package org.markmcguire.cardcollectors.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.markmcguire.cardcollectors.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PlayerRepositoryTest {

  @Autowired
  private PlayerRepository playerRepository;

  @Test
  void savePlayer() {
    Player player = Player.builder()
        .email("test@test.com").password("encrypted")
        .build();

    playerRepository.save(player);

    Player found = playerRepository.findById(1L).orElse(null);

    assertThat(found).isNotNull();
    assertThat(found.getEmail()).isEqualTo(player.getEmail());
  }

  @Test
  void findByEmail() {
    Player player = Player.builder()
        .email("test1@test.com").password("encrypted")
        .build();
    playerRepository.save(player);

    Player found = playerRepository.findByEmail("test1@test.com");

    assertThat(found).isNotNull();
    assertThat(found.getEmail()).isEqualTo(player.getEmail());
  }
}