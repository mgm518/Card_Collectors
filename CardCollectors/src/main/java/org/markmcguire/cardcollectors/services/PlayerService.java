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

  void purchasePack(Player player, PackType packType);

  void purchaseCurrency(Player player, Integer amount);

  void updatePlayer(Player player, Pack pack, List<Card> cardList);

  void discardCard(Player player, Card card);
}
