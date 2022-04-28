package code.backend.persitence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import code.backend.persitence.entities.CourseOffering;
import code.backend.persitence.entities.DateExam;

@Repository
public interface DateExamRepository extends JpaRepository<DateExam, Integer> {
}
