package code.backend.persitence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import code.backend.persitence.entities.ResetToken;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken, String> {
    boolean existsByResetTokenContent(String token);

    ResetToken findByResetTokenContent(String token);

}
