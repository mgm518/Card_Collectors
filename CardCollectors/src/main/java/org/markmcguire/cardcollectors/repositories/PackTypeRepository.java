package org.markmcguire.cardcollectors.repositories;

import org.markmcguire.cardcollectors.models.PackType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PackTypeRepository extends JpaRepository<PackType, Long> {

  @Query("SELECT p FROM PackType p WHERE p.name = :name")
  PackType findByName(@Param("name") String name);
}
