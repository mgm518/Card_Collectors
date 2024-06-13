package org.markmcguire.cardcollectors.repositories;

import org.markmcguire.cardcollectors.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

  @Query("SELECT p FROM Player p WHERE p.email = :email")
  Player findByEmail(@Param("email") String email);
}
