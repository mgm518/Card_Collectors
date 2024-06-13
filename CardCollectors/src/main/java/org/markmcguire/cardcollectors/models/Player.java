package org.markmcguire.cardcollectors.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Player implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;
  String username;
  String password;
  @NonNull
  @Column(nullable = false, unique = true)
  String email;
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Role> roles;
  Integer currency;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  List<Pack> packs = new ArrayList<>();
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  List<Card> collection = new ArrayList<>();

  public void addPack(Pack pack) {
    this.packs.add(pack);
    pack.setUser(this);
  }

  public void removePack(Pack pack) {
    this.packs.remove(pack);
    pack.setUser(null);
  }

  public void addCard(Card card) {
    this.collection.add(card);
    card.setUser(this);
  }

  public void addCards(List<Card> cards) {
    cards.forEach(this::addCard);
  }

  public void removeCard(Card card) {
    this.collection.remove(card);
    card.setUser(null);
  }


  /**
   * Returns the authorities granted to the user. Cannot return <code>null</code>.
   *
   * @return the authorities, sorted by natural key (never <code>null</code>)
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("USER"));
  }

  /**
   * Indicates whether the user's account has expired. An expired account cannot be authenticated.
   *
   * @return <code>true</code> if the user's account is valid (ie non-expired),
   * <code>false</code> if no longer valid (ie expired)
   */
  @Override
  public boolean isAccountNonExpired() {
//    return UserDetails.super.isAccountNonExpired();
    return true;
  }

  /**
   * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
   *
   * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
   */
  @Override
  public boolean isAccountNonLocked() {
//    return UserDetails.super.isAccountNonLocked();
    return true;
  }

  /**
   * Indicates whether the user's credentials (password) has expired. Expired credentials prevent
   * authentication.
   *
   * @return <code>true</code> if the user's credentials are valid (ie non-expired),
   * <code>false</code> if no longer valid (ie expired)
   */
  @Override
  public boolean isCredentialsNonExpired() {
//    return UserDetails.super.isCredentialsNonExpired();
    return true;
  }

  /**
   * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
   *
   * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
   */
  @Override
  public boolean isEnabled() {
//    return UserDetails.super.isEnabled();
    return true;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }
}
