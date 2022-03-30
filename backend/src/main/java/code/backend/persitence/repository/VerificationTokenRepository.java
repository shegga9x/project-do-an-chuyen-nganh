package code.backend.persitence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import code.backend.persitence.entities.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    boolean existsByVerificationTokenContent(String token);

    Optional<VerificationToken> findByVerificationTokenContent(String token);
}
