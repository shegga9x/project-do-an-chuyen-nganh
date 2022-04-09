package code.backend.persitence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import code.backend.persitence.entities.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    @Query("SELECT p FROM Schedule p WHERE p.idSchedule IN ?1")
    List<Schedule> findAllByIds(List<String> ids);

    @Query("SELECT p FROM Schedule p WHERE p.idCourseOffering = ?1")
    List<Schedule> findByIdCourseOffering(String idCourseOffering);

}
