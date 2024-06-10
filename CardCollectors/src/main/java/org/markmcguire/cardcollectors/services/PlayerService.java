package org.markmcguire.cardcollectors.services;

import org.markmcguire.cardcollectors.models.Player;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface PlayerService extends UserDetailsService {

  void savePlayer(Player user);

  //  List<Player> getAllUsers();
  Player getUserById(long id);

  void deleteUser(long id);

  Player findByEmail(String email);
}
