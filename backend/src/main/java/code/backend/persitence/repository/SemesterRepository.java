package code.backend.persitence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import code.backend.persitence.entities.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, String> {

    @Query("SELECT p FROM Semester p WHERE GETDATE() BETWEEN start_Date AND end_Date")
    Semester getCurrentSemester();
}
