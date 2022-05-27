package code.backend.persitence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import code.backend.persitence.entities.StudentSchedule;
import code.backend.persitence.entities.StudentScheduleId;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentScheduleRepository extends JpaRepository<StudentSchedule, StudentScheduleId> {
    @Override
    Optional<StudentSchedule> findById(StudentScheduleId studentScheduleId);

    @Query(value = "select DISTINCT top 3 ID_Semester from Student_Schedule order by ID_Semester desc",nativeQuery = true)
    List<String> findTop3ByIdSemester(String idStudent);

    List<StudentSchedule> findByIdStudent(String idStudent);
}
