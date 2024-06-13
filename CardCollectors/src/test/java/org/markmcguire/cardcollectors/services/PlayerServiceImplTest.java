package org.markmcguire.cardcollectors.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.markmcguire.cardcollectors.models.Pack;
import org.markmcguire.cardcollectors.models.PackType;
import org.markmcguire.cardcollectors.models.Player;
import org.markmcguire.cardcollectors.models.Role;
import org.markmcguire.cardcollectors.repositories.PlayerRepository;
import org.markmcguire.cardcollectors.repositories.RoleRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class PlayerServiceImplTest {

  @Mock
  private RoleRepository roleRepository;
  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private PlayerServiceImpl playerService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void savePlayer() {
    // Given
    Player player = Player.builder()
        .username("testPlayer")
        .email("test@player.com")
        .password("password").build();
    Role role = Role.builder().name("ROLE_USER").build();

    when(passwordEncoder.encode(player.getPassword())).thenReturn("encodedPassword");
    when(roleRepository.findByName("ROLE_USER"))
        .thenReturn(Optional.ofNullable(role));

    playerService.savePlayer(player);

    assertThat(player.getPassword()).isEqualTo("encodedPassword");
    assertThat(player.getRoles()).contains(role);
  }

  @Test
  void purchasePack() {
    // Given
    Player player = Player.builder()
        .id(1L).email("test@player.com").currency(3000).build();
    PackType packType = PackType.builder()
        .id(1L).name("Test Pack").build();
    Pack pack = Pack.builder().type(packType).build();


    playerService.purchasePack(player, packType);

    assertThat(player.getPacks()).isNotEmpty()
        .contains(pack);
    assertThat(player.getPacks().get(0).getType()).isEqualTo(packType);
  }
}