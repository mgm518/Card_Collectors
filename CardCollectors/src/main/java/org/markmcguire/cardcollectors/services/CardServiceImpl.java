package org.markmcguire.cardcollectors.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.markmcguire.cardcollectors.models.CardType;
import org.markmcguire.cardcollectors.models.PackType;
import org.markmcguire.cardcollectors.models.Rarity;
import org.markmcguire.cardcollectors.repositories.CardTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CardServiceImpl implements CardService {

  private final CardTypeRepository cardTypeRepository;

  @Autowired
  public CardServiceImpl(CardTypeRepository cardTypeRepository) {
    this.cardTypeRepository = cardTypeRepository;
  }

  @Transactional
  public CardType createCardType(CardType cardType) {
    return cardTypeRepository.save(cardType);
  }

  @Override
  public Set<CardType> getAllCards() {
    return Set.copyOf(cardTypeRepository.findAll());
  }

  /**
   * This simulates a standard pull.  No bonuses should ever be applied to the rarities and
   * individual cards
   *
   * @return
   */
  @Override
  public CardType gachaStandardPull() {
    Rarity pick = pickRarity(0);
    log.debug("standard rarity pick: {}", pick);
    List<CardType> cardTypeList = getAllCardsByRarity(pick);
    return cardTypeList.get(ThreadLocalRandom.current().nextInt(cardTypeList.size()));
  }

  /**
   * This simulates a limited-time pull.  Bonus rates are applied so higher rarities are more likely
   * to emerge. Something that can be expanded upon is applying a "featured" bonus for individual
   * cards.
   *
   * @return
   */
  @Override
  public CardType gachaLimitedPull() {
    Rarity pick = pickRarity(5);
    log.debug("limited rarity pick: {}", pick);
    List<CardType> cardTypeList = getAllCardsByRarity(pick);
    return cardTypeList.get(ThreadLocalRandom.current().nextInt(cardTypeList.size()));
  }

  /**
   * This simulates opening the given card pack.
   *
   * @param packType The selected card park
   * @return The list of cards selected from the pool of cards the pack has access to.
   */
  // TODO implement logic for featured cards
  @Override
  public List<CardType> openCardPack(PackType packType) {
    List<CardType> pulls = new ArrayList<>();
    for (int i = 0; i < packType.getSize(); i++) {
      Rarity pick = pickRarity((i == 0) ? 5 + packType.getBonus() : packType.getBonus());
      List<CardType> filtered = packType.getPool().stream()
          .filter(card -> card.getRarity().equals(pick))
          .toList();
      pulls.add(filtered.get(ThreadLocalRandom.current().nextInt(filtered.size())));
    }
    return pulls;
  }

  @Transactional
  public List<CardType> initializeCardDb() {
    cardTypeRepository.deleteAll();
    List<CardType> cardTypeList = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      cardTypeList.add(CardType.builder()
          .name("SSR Unit #" + i)
          .rarity(Rarity.SSR)
          .build());
    }
    for (int i = 0; i < 10; i++) {
      cardTypeList.add(CardType.builder()
          .name("SR Unit #" + i)
          .rarity(Rarity.SR)
          .build());
    }
    for (int i = 0; i < 10; i++) {
      cardTypeList.add(CardType.builder()
          .name("R Unit #" + i)
          .rarity(Rarity.R)
          .build());
    }
    return cardTypeRepository.saveAll(cardTypeList);
  }

  private List<CardType> getAllCardsByRarity(Rarity rarity) {
    return cardTypeRepository.findCardsByRarity(rarity);
  }

  /**
   * Will select a rarity of card that will be drawn.
   *
   * @param bonus A percentage bonus for <b>SSR</b> rarities; depends on the banner used.
   * @return Selected rarity for list of cards to draw.
   */
  private Rarity pickRarity(int bonus) {
    int pick = ThreadLocalRandom.current().nextInt(0, 100);
    if (pick < Rarity.SSR.getWeight() + bonus) {
      return Rarity.SSR;
    } else if (pick < Rarity.SR.getWeight()) {
      return Rarity.SR;
    }
    return Rarity.R;
  }
}
