package uoggmk.college.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uoggmk.college.Models.Subject;
import uoggmk.college.Repositories.SubjectRepository;
import uoggmk.college.Services.Exceptions.*;

import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public void add(Subject subject) throws SubjectAlreadyExistsException {
        Optional<Subject> findedSubject =
            subjectRepository.findByNameAndGroupId(subject.getName(),subject.getGroup().getId());
        if(findedSubject.isPresent()) throw new SubjectAlreadyExistsException();
        subjectRepository.save(subject);
    }

    public Subject findById(Long id) throws SubjectNotFoundException{
        Optional<Subject> subject = subjectRepository.findById(id);
        if(!subject.isPresent()) throw new SubjectNotFoundException();
        return subject.get();
    }
}
