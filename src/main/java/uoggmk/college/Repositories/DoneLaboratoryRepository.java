package uoggmk.college.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uoggmk.college.Models.DoneLaboratory;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DoneLaboratoryRepository extends JpaRepository<DoneLaboratory, Long> {
    Optional<DoneLaboratory> findByOriginalNameAndSubjectIdAndUserId(String originalName, Long subjectId, Long userId);
    Set<DoneLaboratory> findBySubjectId(Long subjectId);
}
