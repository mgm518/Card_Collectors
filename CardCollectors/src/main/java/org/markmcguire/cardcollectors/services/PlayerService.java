package org.markmcguire.cardcollectors.services;

import org.markmcguire.cardcollectors.models.PackType;
import org.markmcguire.cardcollectors.models.Player;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface PlayerService extends UserDetailsService {

  void savePlayer(Player player);

  //  List<Player> getAllUsers();
  Player getUserById(long id);

  void deleteUser(long id);

  Player findByEmail(String email);

  void purchasePack(Player player, PackType packType);
}
