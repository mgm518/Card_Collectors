package org.markmcguire.cardcollectors.services;

import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
   * @param email the username identifying the user whose data is required.
   * @return a fully populated user record (never <code>null</code>)
   * @throws UsernameNotFoundException if the user could not be found or the user has no
   *                                   GrantedAuthority
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Player user = playerRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("User not found with email: " + email);
    }
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
    );
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
   * This function will act as a simple transaction wherein the player purchases a {@link Pack}.
   * That player's currency is deducted based on the {@link PackType}'s cost.
   *
   * @param player   The user currently logged in.
   * @param packType The pack type being purchased.
   */
  @Override
  @Transactional
  public void purchasePack(Player player, PackType packType) {
    player.addPack(Pack.builder().type(packType).build());
    player.setCurrency(player.getCurrency() - packType.getCost());
    playerRepository.save(player);
  }

  /**
   * This function will act as a simple transaction wherein the player purchases in-system
   * currency.
   *
   * @param player The user currently logged in.
   * @param amount The amount to add
   */
  @Override
  @Transactional
  public void purchaseCurrency(Player player, Integer amount) {
    player.setCurrency(player.getCurrency() + amount);
    playerRepository.save(player);
  }

  /**
   * Stimulates the opening of a pack of cards.  The list of {@link Card}'s is added to the
   * {@link Player}'s collection, and the opened {@link Pack} is removed from the player's
   * inventory.
   *
   * @param player   The user currently logged in.
   * @param pack     The pack being opened
   * @param cardList The list of cards obtained from the pack
   */
  @Override
  @Transactional
  public void addCardsToPlayerCollection(Player player, Pack pack, List<Card> cardList) {
    // TODO implement adding cardList to player's collection
    player.addCards(cardList);
    player.removePack(pack);
    playerRepository.save(player);
  }

  /**
   * The {@link Player} removes a {@link Card} from the collection
   *
   * @param player The user currently logged in.
   * @param card   The card to be discarded.
   */
  @Override
  @Transactional
  public void discardCard(Player player, Card card) {
    player.removeCard(card);
    playerRepository.save(player);
  }
}
