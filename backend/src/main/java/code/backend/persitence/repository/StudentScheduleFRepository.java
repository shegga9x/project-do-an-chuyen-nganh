package code.backend.persitence.repository;

import code.backend.persitence.entities.StudentScheduleF;
import code.backend.persitence.entities.StudentScheduleFId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentScheduleFRepository extends JpaRepository<StudentScheduleF, StudentScheduleFId> {

    Optional<StudentScheduleF> findById(StudentScheduleFId id);

    @Query("SELECT stF FROM StudentScheduleF stF WHERE stF.idSemester = ?1 and stF.idStudent =?2")
    List<StudentScheduleF> findByIdSemesterAndIdStudent(String idSemester,String idStudent);

    @Query("SELECT stF.idSchedule FROM StudentScheduleF stF WHERE stF.idSemester = ?1 and stF.idStudent =?2")
    List<String> findIdScheduleByIdSemesterAndIdStudent(String idSemester,String idStudent);

}
