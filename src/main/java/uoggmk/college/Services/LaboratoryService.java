package uoggmk.college.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uoggmk.college.Models.Laboratory;
import uoggmk.college.Repositories.LaboratoryRepository;
import uoggmk.college.Services.Exceptions.LaboratoryAlreadyExistsException;

import java.util.Optional;

@Service
public class LaboratoryService {
    @Autowired
    private LaboratoryRepository laboratoryRepository;

    public void addLaboratory(Laboratory laboratory) throws LaboratoryAlreadyExistsException {
        Optional<Laboratory> laboratory1 = laboratoryRepository.findByOriginalNameAndSubjectId(laboratory.getOriginalName(), laboratory.getSubject().getId());
        if(laboratory1.isPresent()) throw new LaboratoryAlreadyExistsException();
        laboratoryRepository.save(laboratory);
    }
}
