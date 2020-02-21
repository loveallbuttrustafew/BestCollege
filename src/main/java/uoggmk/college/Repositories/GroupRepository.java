package uoggmk.college.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uoggmk.college.Models.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);
    Optional<Group> findByNameAndNumberAndCourse(String name, Byte Number, Byte course);
    Optional<Group> findById(Long id);
}
