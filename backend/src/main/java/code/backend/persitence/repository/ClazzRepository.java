package code.backend.persitence.repository;

import code.backend.persitence.entities.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClazzRepository extends JpaRepository<Clazz, String> {
    List<Clazz> findByClazzCodeLike(String Clazz_code);
}
