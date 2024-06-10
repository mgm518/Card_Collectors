package org.markmcguire.cardcollectors.repositories;

import java.util.List;
import org.markmcguire.cardcollectors.models.Card;
import org.markmcguire.cardcollectors.models.Rarity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

  public List<Card> findCardsByRarity(Rarity rarity);
}
