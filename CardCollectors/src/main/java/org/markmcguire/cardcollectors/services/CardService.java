package org.markmcguire.cardcollectors.services;

import java.util.List;
import java.util.Set;
import org.markmcguire.cardcollectors.models.CardType;
import org.markmcguire.cardcollectors.models.PackType;

public interface CardService {

  Set<CardType> getAllCards();

  /**
   * This simulates a standard pull.  No bonuses should ever be applied to the rarities and
   * individual cards
   *
   * @return The result of the gacha pull
   */
  CardType gachaStandardPull();

  /**
   * This simulates a limited-time pull.  Bonus rates are applied so higher rarities are more likely
   * to emerge. Something that can be expanded upon is applying a "featured" bonus for individual
   * cards.
   *
   * @return The result of the gacha pull
   */
  CardType gachaLimitedPull();

  /**
   * This simulates opening the given card pack.
   *
   * @param packType The selected card park
   * @return The list of cards selected from the pool of cards the pack has access to.
   */
  List<CardType> openCardPack(PackType packType);
}
