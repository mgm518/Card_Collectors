package org.markmcguire.cardcollectors.repositories;

import java.util.List;
import org.markmcguire.cardcollectors.models.CardType;
import org.markmcguire.cardcollectors.models.Rarity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardTypeRepository extends JpaRepository<CardType, Long> {

  List<CardType> findCardsByRarity(Rarity rarity);
}
