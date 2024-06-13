package org.markmcguire.cardcollectors.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.markmcguire.cardcollectors.models.CardType;
import org.markmcguire.cardcollectors.models.PackType;
import org.markmcguire.cardcollectors.models.Rarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PackTypeRepositoryTest {

  @Autowired
  private PackTypeRepository packTypeRepository;

  @Autowired
  private CardTypeRepository cardTypeRepository;


  @Test
  public void testSavePackType() {
    List<CardType> cardList = List.of(
        CardType.builder()
            .name("Test Card #1").rarity(Rarity.SSR)
            .build(),
        CardType.builder()
            .name("Test Card #1").rarity(Rarity.SR)
            .build(),
        CardType.builder()
            .name("Test Card #1").rarity(Rarity.R)
            .build()
    );
    cardTypeRepository.saveAll(cardList);

    PackType pack = PackType.builder()
        .name("Test Pack #1").description("Test Pack Description")
        .pool(Set.copyOf(cardTypeRepository.findAll())).build();
    packTypeRepository.save(pack);

    PackType foundPack = packTypeRepository.findById(pack.getId()).orElse(null);
    assertThat(foundPack).isNotNull();
    assertThat(foundPack.getName()).isEqualTo(pack.getName());
    assertThat(foundPack.getPool()).containsAll(pack.getPool());
  }

  @Test
  void testFindByName() {
    List<CardType> cardList = List.of(
        CardType.builder()
            .name("Test Card #1").rarity(Rarity.SSR)
            .build(),
        CardType.builder()
            .name("Test Card #1").rarity(Rarity.SR)
            .build(),
        CardType.builder()
            .name("Test Card #1").rarity(Rarity.R)
            .build()
    );
    cardTypeRepository.saveAll(cardList);

    PackType pack = PackType.builder()
        .name("Test Pack #2").description("Test Pack Description")
        .pool(Set.copyOf(cardTypeRepository.findAll())).build();
    packTypeRepository.save(pack);

    PackType foundPack = packTypeRepository.findByName(pack.getName());
    assertThat(foundPack).isNotNull();
    assertThat(foundPack.getName()).isEqualTo(pack.getName());
    assertThat(foundPack.getPool()).containsAll(pack.getPool());
  }
}