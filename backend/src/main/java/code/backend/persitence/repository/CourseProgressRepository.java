package code.backend.persitence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import code.backend.persitence.entities.CourseProgress;

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Integer> {

    List<CourseProgress> findByNumberYear(int number_year);

    @Query(value = "select DISTINCT top 1 cp.number_year from Course_Progress cp order by cp.number_year desc",nativeQuery = true)
    String getLastNumberYear();

}
