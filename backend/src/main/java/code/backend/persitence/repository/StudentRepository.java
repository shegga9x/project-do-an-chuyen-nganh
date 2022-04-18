package code.backend.persitence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import code.backend.persitence.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findByClazzCodeLike(String Clazz_code);
}
