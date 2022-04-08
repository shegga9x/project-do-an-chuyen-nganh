package code.backend.persitence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import code.backend.persitence.entities.StudentSchedule;
import code.backend.persitence.entities.StudentScheduleId;

@Repository
public interface StudentScheduleRepository extends JpaRepository<StudentSchedule, StudentScheduleId> {

}
