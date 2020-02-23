package uoggmk.college.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uoggmk.college.Models.DoneLaboratory;
import uoggmk.college.Models.Laboratory;
import uoggmk.college.Repositories.DoneLaboratoryRepository;
import uoggmk.college.Repositories.LaboratoryRepository;
import uoggmk.college.Services.Exceptions.DoneLaboratoryAlreadyExistsException;
import uoggmk.college.Services.Exceptions.LaboratoryAlreadyExistsException;

import java.util.Optional;
import java.util.Set;

@Service
public class DoneLaboratoryService {
    @Autowired
    private DoneLaboratoryRepository doneLaboratoryRepository;

    public void addDoneLaboratory(DoneLaboratory laboratory) throws DoneLaboratoryAlreadyExistsException {
        Optional<DoneLaboratory> laboratory1 = doneLaboratoryRepository.findByOriginalNameAndSubjectId(laboratory.getOriginalName(), laboratory.getSubject().getId());
        if(laboratory1.isPresent()) throw new DoneLaboratoryAlreadyExistsException();
        doneLaboratoryRepository.save(laboratory1.get());
    }

    public Set<DoneLaboratory> getAllLaboratories(Long subjectId) {
        return doneLaboratoryRepository.findBySubjectId(subjectId);
    }
}
