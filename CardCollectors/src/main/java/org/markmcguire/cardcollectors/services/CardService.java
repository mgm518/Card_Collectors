package org.markmcguire.cardcollectors.services;

import java.util.ArrayList;
import java.util.List;
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
public class CardService {

  private final CardRepository cardRepository;

  @Autowired
  public CardService(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  @Transactional
  public Card createCard(Card card) {
    return cardRepository.save(card);
  }

  public List<Card> getAllCards() {
    return cardRepository.findAll();
  }

  /**
   * This simulates a standard pull.  No bonuses should ever be applied to the rarities and
   * individual cards
   *
   * @return
   */
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
  public Card gachaLimitedPull() {
    Rarity pick = pickRarity(5);
    log.debug("limited rarity pick: {}", pick);
    List<Card> cardList = getAllCardsByRarity(pick);
    return cardList.get(ThreadLocalRandom.current().nextInt(cardList.size()));
  }

  // TODO implement logic for featured cards
  public List<Card> openCardPack(Pack pack) {
    List<Card> pulls = new ArrayList<>();
    for (int i = 0; i < pack.getSize(); i++) {
      Rarity pick = pickRarity((i==0) ? 5 : 0);
      List<Card> filtered = pack.getPool().stream()
          .filter(card -> card.getRarity().equals(pick))
          .toList();
      pulls.add(filtered.get(ThreadLocalRandom.current().nextInt(filtered.size())));
    }
    return pulls;
  }

  public List<Card> getAllCardsByRarity(Rarity rarity) {
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
