package net.rahuls.hitpixel.data.repository;

import net.rahuls.hitpixel.data.entity.Transaction;
import net.rahuls.hitpixel.data.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findTransactionById(Long id);

    boolean existsById(Long id);

    List<Transaction> findAllByUser(User user, Pageable pageable);

    Optional<Transaction> findByIdAndUser(Long id, User user);
}