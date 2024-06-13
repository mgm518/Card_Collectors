package org.markmcguire.cardcollectors.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.markmcguire.cardcollectors.models.CardType;
import org.markmcguire.cardcollectors.models.Rarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CardTypeRepositoryTest {

  @Autowired
  private CardTypeRepository cardTypeRepository;

  @Test
  public void testSaveCardType() {
    CardType card = CardType.builder()
        .name("Test Card").rarity(Rarity.SSR)
        .build();
    cardTypeRepository.save(card);
    CardType foundCard = cardTypeRepository.findById(card.getId()).orElse(null);
    assertThat(foundCard).isNotNull();
    assertThat(foundCard.getName()).isEqualTo(card.getName());
  }

  @ParameterizedTest
  @ValueSource(ints = {5, 10, 20})
  public void testFindCardsByRarity(int size) {
    List<CardType> cardTypeList = new ArrayList<>();
    for (int i = 0; i < size*4; i++) {
      cardTypeList.add(CardType.builder()
          .name("SSR Unit #" + i)
          .rarity(Rarity.SSR)
          .build());
    }
    for (int i = 0; i < size*2; i++) {
      cardTypeList.add(CardType.builder()
          .name("SR Unit #" + i)
          .rarity(Rarity.SR)
          .build());
    }
    for (int i = 0; i < size; i++) {
      cardTypeList.add(CardType.builder()
          .name("R Unit #" + i)
          .rarity(Rarity.R)
          .build());
    }
    cardTypeRepository.saveAll(cardTypeList);

    List<CardType> rarityList = cardTypeRepository.findCardsByRarity(Rarity.R);

    assertThat(rarityList).isNotEmpty();
    assertThat(rarityList.size()).isEqualTo(size);
    var card = rarityList.get(0);
    assertThat(card.getRarity()).isEqualTo(Rarity.R);
  }

}