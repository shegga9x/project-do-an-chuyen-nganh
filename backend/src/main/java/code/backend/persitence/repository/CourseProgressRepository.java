package code.backend.persitence.repository;

import code.backend.persitence.entities.CourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Integer> {

    List<CourseProgress> findByNumberYear(int number_year);

    @Query(value = "select DISTINCT top 1 cp.number_year from Course_Progress cp order by cp.number_year desc",nativeQuery = true)
    String getLastNumberYear();

}
