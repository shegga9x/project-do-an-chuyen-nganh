// ackage code.backend.persitence.repository;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.stereotype.Repository;

// import code.backend.helpers.payload.response.Professor;
// import code.backend.persitence.entities.ProfessorSchedule;
// import code.backend.persitence.entities.ProfessorScheduleId;

// @Repository
// public interface ProfessorRepository extends JpaRepository<Profe, String> {

//     Optional<ProfessorSchedule> findById(ProfessorScheduleId id);

//     @Query("SELECT stF FROM ProfessorSchedule stF ")
//     List<ProfessorSchedule> findByIdSemesterAndIdProfessor(String idSemester, String idProfessor);}