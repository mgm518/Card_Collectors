package org.markmcguire.cardcollectors.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.markmcguire.cardcollectors.models.Card;
import org.markmcguire.cardcollectors.models.Pack;
import org.markmcguire.cardcollectors.models.PackType;
import org.markmcguire.cardcollectors.models.Player;
import org.markmcguire.cardcollectors.models.Role;
import org.markmcguire.cardcollectors.repositories.PlayerRepository;
import org.markmcguire.cardcollectors.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerServiceImpl implements PlayerService {

  private final PlayerRepository playerRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public PlayerServiceImpl(PlayerRepository playerRepository, RoleRepository roleRepository,
      PasswordEncoder passwordEncoder) {
    this.playerRepository = playerRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }


  /**
   * Locates the user based on the username. In the actual implementation, the search may possibly
   * be case sensitive, or case insensitive depending on how the implementation instance is
   * configured. In this case, the <code>UserDetails</code> object that comes back may have a
   * username that is of a different case than what was actually requested..
   *
   * @param username the username identifying the user whose data is required.
   * @return a fully populated user record (never <code>null</code>)
   * @throws UsernameNotFoundException if the user could not be found or the user has no
   *                                   GrantedAuthority
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return null;
  }

  @Override
  @Transactional
  public void savePlayer(Player player) {
    player.setPassword(passwordEncoder.encode(player.getPassword()));
    Role userRole = checkRoleExist("ROLE_USER");
    player.setRoles(Arrays.asList(userRole));
    playerRepository.save(player);
  }

  private Role checkRoleExist(String roleName) {
    return roleRepository.findByName(roleName)
        .orElseGet(() -> roleRepository.save(Role.builder().name(roleName).build()));
  }

  @Override
  public Player getUserById(long id) {
    return playerRepository.findById(id).orElse(null);
  }

  @Override
  @Transactional
  public void deleteUser(long id) {
    Optional.ofNullable(getUserById(id))
        .ifPresent(playerRepository::delete);
  }

  @Override
  public Player findByEmail(String email) {
    return playerRepository.findByEmail(email);
  }

  /**
   * @param player
   * @param packType
   */
  @Override
  @Transactional
  public void purchasePack(Player player, PackType packType) {
    player.addPack(Pack.builder().type(packType).build());
    player.setCurrency(player.getCurrency() - packType.getCost());
    playerRepository.save(player);
  }

  /**
   * @param player
   * @param amount
   */
  @Override
  @Transactional
  public void purchaseCurrency(Player player, Integer amount) {
    player.setCurrency(player.getCurrency() + amount);
    playerRepository.save(player);
  }

  /**
   * @param player
   * @param pack
   * @param cardList
   */
  @Override
  @Transactional
  public void updatePlayer(Player player, Pack pack, List<Card> cardList) {
    // TODO implement adding cardList to player's collection
    player.addCards(cardList);
    player.removePack(pack);
    playerRepository.save(player);
  }

  /**
   * @param player
   * @param card
   */
  @Override
  @Transactional
  public void discardCard(Player player, Card card) {
    player.removeCard(card);
    playerRepository.save(player);
  }
}
