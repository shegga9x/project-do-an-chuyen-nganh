package code.backend.persitence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import code.backend.persitence.entities.AccountDetail;

@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, String> {

}
