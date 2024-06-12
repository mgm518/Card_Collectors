package org.markmcguire.cardcollectors.repositories;

import org.markmcguire.cardcollectors.models.PackType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackTypeRepository extends JpaRepository<PackType, Long> {

  PackType findByName(String name);
}
