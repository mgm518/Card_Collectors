package org.markmcguire.cardcollectors.services;

import java.util.List;
import org.markmcguire.cardcollectors.models.Card;
import org.markmcguire.cardcollectors.models.CardType;
import org.markmcguire.cardcollectors.models.Pack;
import org.markmcguire.cardcollectors.models.PackType;

public interface PackService {

  /**
   * Creates a {@link PackType} and saves it to the repository.  Should the {@code packType} not
   * have a pool of {@link CardType} already set, then it will be set to the list of
   * {@code CardTypes} already in the database.
   *
   * @param packType {@link PackType} being created.
   * @return the resulting {@link PackType} stored in the database.
   */
  PackType createPackType(PackType packType);

  PackType getPackType(String name);

  Pack getPack(Long id);

  /**
   * Takes the {@code pack} and calls {@link CardService#openCardPack(PackType)} to generate the
   * random list of {@link CardType}.  They are converted to {@link Card} so the
   * {@link org.markmcguire.cardcollectors.models.Player} can store them.
   *
   * @param pack The selected Pack being opened
   * @return The resulting Cards from the gacha generation.
   */
  List<Card> openPack(Pack pack);

}
