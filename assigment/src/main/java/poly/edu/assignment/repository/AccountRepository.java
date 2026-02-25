package poly.edu.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.assignment.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByToken(String token);
}