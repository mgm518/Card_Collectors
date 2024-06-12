package org.markmcguire.cardcollectors.repositories;

import java.util.Optional;
import org.markmcguire.cardcollectors.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(String roleUser);
}
