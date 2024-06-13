package org.markmcguire.cardcollectors.services;


import java.util.List;
import org.markmcguire.cardcollectors.models.Card;
import org.markmcguire.cardcollectors.models.Pack;
import org.markmcguire.cardcollectors.models.PackType;
import org.markmcguire.cardcollectors.models.Player;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface PlayerService extends UserDetailsService {

  void savePlayer(Player player);

  Player getUserById(long id);

  void deleteUser(long id);

  Player findByEmail(String email);

  /**
   * This function will act as a simple transaction wherein the player purchases a {@link Pack}.
   * That player's currency is deducted based on the {@link PackType}'s cost.
   *
   * @param player   The user currently logged in.
   * @param packType The pack type being purchased.
   */
  void purchasePack(Player player, PackType packType);

  /**
   * This function will act as a simple transaction wherein the player purchases in-system
   * currency.
   *
   * @param player The user currently logged in.
   * @param amount The amount to add
   */
  void purchaseCurrency(Player player, Integer amount);

  /**
   * Stimulates the opening of a pack of cards.  The list of {@link Card}'s is added to the
   * {@link Player}'s collection, and the opened {@link Pack} is removed from the player's
   * inventory.
   *
   * @param player   The user currently logged in.
   * @param pack     The pack being opened
   * @param cardList The list of cards obtained from the pack
   */
  void addCardsToPlayerCollection(Player player, Pack pack, List<Card> cardList);

  /**
   * The {@link Player} removes a {@link Card} from the collection
   *
   * @param player The user currently logged in.
   * @param card   The card to be discarded.
   */
  void discardCard(Player player, Card card);
}
