package org.markmcguire.cardcollectors.repositories;

import org.markmcguire.cardcollectors.models.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {

  Pack findByName(String name);
}
