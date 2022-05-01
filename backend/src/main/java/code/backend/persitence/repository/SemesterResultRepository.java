package code.backend.persitence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import code.backend.persitence.entities.SemesterResult;
import code.backend.persitence.entities.SemesterResultId;

@Repository

public interface SemesterResultRepository extends JpaRepository<SemesterResult, SemesterResultId> {

    @Query("SELECT stF FROM SemesterResult stF WHERE stF.idStudent = ?1")
    List<SemesterResult> findByIdStudent(String idStudent);

}
