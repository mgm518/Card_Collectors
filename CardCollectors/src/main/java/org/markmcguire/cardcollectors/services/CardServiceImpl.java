package org.markmcguire.cardcollectors.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.markmcguire.cardcollectors.models.Card;
import org.markmcguire.cardcollectors.models.Pack;
import org.markmcguire.cardcollectors.models.Rarity;
import org.markmcguire.cardcollectors.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CardServiceImpl implements CardService {

  private final CardRepository cardRepository;

  @Autowired
  public CardServiceImpl(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  @Transactional
  public Card createCard(Card card) {
    return cardRepository.save(card);
  }

  @Override
  public Set<Card> getAllCards() {
    return Set.copyOf(cardRepository.findAll());
  }

  /**
   * This simulates a standard pull.  No bonuses should ever be applied to the rarities and
   * individual cards
   *
   * @return
   */
  @Override
  public Card gachaStandardPull() {
    Rarity pick = pickRarity(0);
    log.debug("standard rarity pick: {}", pick);
    List<Card> cardList = getAllCardsByRarity(pick);
    return cardList.get(ThreadLocalRandom.current().nextInt(cardList.size()));
  }

  /**
   * This simulates a limited-time pull.  Bonus rates are applied so higher rarities are more likely
   * to emerge. Something that can be expanded upon is applying a "featured" bonus for individual
   * cards.
   *
   * @return
   */
  @Override
  public Card gachaLimitedPull() {
    Rarity pick = pickRarity(5);
    log.debug("limited rarity pick: {}", pick);
    List<Card> cardList = getAllCardsByRarity(pick);
    return cardList.get(ThreadLocalRandom.current().nextInt(cardList.size()));
  }

  /**
   * This simulates opening the given card pack.
   *
   * @param pack The selected card park
   * @return The list of cards selected from the pool of cards the pack has access to.
   */
  // TODO implement logic for featured cards
  @Override
  public List<Card> openCardPack(Pack pack) {
    List<Card> pulls = new ArrayList<>();
    for (int i = 0; i < pack.getSize(); i++) {
      Rarity pick = pickRarity((i == 0) ? 5 + pack.getBonus() : pack.getBonus());
      List<Card> filtered = pack.getPool().stream()
          .filter(card -> card.getRarity().equals(pick))
          .toList();
      pulls.add(filtered.get(ThreadLocalRandom.current().nextInt(filtered.size())));
    }
    return pulls;
  }

  @Transactional
  public List<Card> initializeCardDb() {
    cardRepository.deleteAll();
    List<Card> cardList = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      cardList.add(Card.builder()
          .name("SSR Unit #" + i)
          .rarity(Rarity.SSR)
          .build());
    }
    for (int i = 0; i < 10; i++) {
      cardList.add(Card.builder()
          .name("SR Unit #" + i)
          .rarity(Rarity.SR)
          .build());
    }
    for (int i = 0; i < 10; i++) {
      cardList.add(Card.builder()
          .name("R Unit #" + i)
          .rarity(Rarity.R)
          .build());
    }
    return cardRepository.saveAll(cardList);
  }

  private List<Card> getAllCardsByRarity(Rarity rarity) {
    return cardRepository.findCardsByRarity(rarity);
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
