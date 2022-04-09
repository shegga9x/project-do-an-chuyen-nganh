package code.backend.persitence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import code.backend.persitence.entities.CourseOffering;

@Repository
public interface CourseOfferingRepository extends JpaRepository<CourseOffering, String> {
     Optional<CourseOffering> findByIdCourse(String idCourse);
}