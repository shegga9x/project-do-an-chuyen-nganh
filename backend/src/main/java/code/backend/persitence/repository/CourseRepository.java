package code.backend.persitence.repository;

import code.backend.persitence.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {


}
