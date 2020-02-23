package uoggmk.college.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uoggmk.college.Models.Laboratory;

import java.util.Optional;
import java.util.Set;

@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {
    Optional<Laboratory> findByOriginalNameAndSubjectId(String originalName, Long subjectId);
    Set<Laboratory> findBySubjectId(Long subjectId);
}
