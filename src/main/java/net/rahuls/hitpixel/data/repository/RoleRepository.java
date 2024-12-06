package net.rahuls.hitpixel.data.repository;

import net.rahuls.hitpixel.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

  default Optional<Role> getUserRole() {
    return findById("USER");
  }
}