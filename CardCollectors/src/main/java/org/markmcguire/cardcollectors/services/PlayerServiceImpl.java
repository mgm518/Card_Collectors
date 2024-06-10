package org.markmcguire.cardcollectors.services;

import java.util.Arrays;
import java.util.Optional;
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
  public PlayerServiceImpl(PlayerRepository playerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
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
  public void savePlayer(Player user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    Role userRole = roleRepository.findByName("ROLE_USER");
    if (userRole == null) {
      userRole = checkRoleExist("ROLE_USER");
    }
    user.setRoles(Arrays.asList(userRole));
    playerRepository.save(user);
  }

  private Role checkRoleExist(String roleName) {
    Role role = new Role();
    role.setName(roleName);
    return roleRepository.save(role);
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
}
