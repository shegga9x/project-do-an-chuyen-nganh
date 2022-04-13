package code.backend.persitence.repository;

import code.backend.persitence.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, String> {
    Optional<Faculty> findByIdFaculty(String IdFaculty);
}
