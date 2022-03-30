package code.backend.persitence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import code.backend.persitence.entities.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByAccountId(String userID);

    boolean existsByToken(String token);

    @Query("SELECT m FROM RefreshToken m  WHERE m.accountId = ?1")
    List<RefreshToken> getTokenListOfUser(String accountId);

}
