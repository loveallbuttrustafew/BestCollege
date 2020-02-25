package uoggmk.college.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uoggmk.college.Models.DoneLaboratory;
import uoggmk.college.Repositories.DoneLaboratoryRepository;
import uoggmk.college.Services.Exceptions.LaboratoryAlreadyExistsException;

import java.util.Optional;
import java.util.Set;

@Service
public class DoneLaboratoryService {
    @Autowired
    private DoneLaboratoryRepository doneLaboratoryRepository;

    public void addDoneLaboratory(DoneLaboratory laboratory) throws LaboratoryAlreadyExistsException {
        Optional<DoneLaboratory> laboratory1 = doneLaboratoryRepository.findByOriginalNameAndSubjectIdAndUserId(laboratory.getOriginalName(), laboratory.getSubject().getId(), laboratory.getUser().getId());
        if(laboratory1.isPresent()) throw new LaboratoryAlreadyExistsException();
        doneLaboratoryRepository.save(laboratory);
    }

    public Set<DoneLaboratory> getAllDoneLaboratories(Long subjectId) {
        return doneLaboratoryRepository.findBySubjectId(subjectId);
    }
}
