package org.markmcguire.cardcollectors.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.markmcguire.cardcollectors.models.Card;
import org.markmcguire.cardcollectors.models.CardType;
import org.markmcguire.cardcollectors.models.Pack;
import org.markmcguire.cardcollectors.models.PackType;
import org.markmcguire.cardcollectors.models.Rarity;
import org.markmcguire.cardcollectors.repositories.PackRepository;
import org.markmcguire.cardcollectors.repositories.PackTypeRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class PackServiceImplTest {

  @Mock
  private CardService cardService;
  @Mock
  private PackTypeRepository packTypeRepository;
  @Mock
  private PackRepository packRepository;

  @InjectMocks
  private PackServiceImpl packService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }


  @Test
  void openPack() {
    // Given
    PackType packType = PackType.builder()
        .name("Test Pack 1")
        .pool(Set.of(
            CardType.builder().name("Test 1").rarity(Rarity.R).build(),
            CardType.builder().name("Test 2").rarity(Rarity.SR).build(),
            CardType.builder().name("Test 3").rarity(Rarity.SSR).build()))
        .build();
    Pack pack = Pack.builder().type(packType).build();


    when(cardService.openCardPack(packType)).thenReturn(List.of(
        CardType.builder().name("Test 3").rarity(Rarity.SSR).build(),
        CardType.builder().name("Test 3").rarity(Rarity.SSR).build(),
        CardType.builder().name("Test 3").rarity(Rarity.SSR).build(),
        CardType.builder().name("Test 3").rarity(Rarity.SSR).build(),
        CardType.builder().name("Test 3").rarity(Rarity.SSR).build(),
        CardType.builder().name("Test 3").rarity(Rarity.SSR).build(),
        CardType.builder().name("Test 3").rarity(Rarity.SSR).build(),
        CardType.builder().name("Test 3").rarity(Rarity.SSR).build(),
        CardType.builder().name("Test 3").rarity(Rarity.SSR).build(),
        CardType.builder().name("Test 3").rarity(Rarity.SSR).build()
    ));

    List<Card> returnedCards = packService.openPack(pack);

    assertThat(returnedCards).isNotNull().isNotEmpty();
    assertThat(returnedCards.size()).isEqualTo(10);
  }
}