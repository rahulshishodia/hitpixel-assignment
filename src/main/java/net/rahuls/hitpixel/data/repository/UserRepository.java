package net.rahuls.hitpixel.data.repository;

import net.rahuls.hitpixel.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsById(Long userId);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}