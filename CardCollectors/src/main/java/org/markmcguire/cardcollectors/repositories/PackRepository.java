package org.markmcguire.cardcollectors.repositories;

import org.markmcguire.cardcollectors.models.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackRepository extends JpaRepository<Pack, Long> {

}
