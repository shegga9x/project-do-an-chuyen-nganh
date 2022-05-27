package code.backend.persitence.repository;

import code.backend.persitence.entities.CourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Integer> {

    List<CourseProgress> findByNumberYear(int number_year);
}
