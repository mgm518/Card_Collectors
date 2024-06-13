package org.markmcguire.cardcollectors.repositories;

import java.util.List;
import org.markmcguire.cardcollectors.models.CardType;
import org.markmcguire.cardcollectors.models.Rarity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardTypeRepository extends JpaRepository<CardType, Long> {

  @Query("SELECT c FROM CardType c WHERE c.rarity = :rarity")
  List<CardType> findCardsByRarity(@Param("rarity") Rarity rarity);
}
