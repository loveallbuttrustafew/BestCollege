package uoggmk.college.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uoggmk.college.Models.Subject;
import uoggmk.college.Models.User;
import uoggmk.college.Repositories.SubjectRepository;
import uoggmk.college.Services.Exceptions.SubjectAlreadyExistsException;
import uoggmk.college.Services.Exceptions.SubjectNotFoundException;
import uoggmk.college.Services.Exceptions.UserNotFoundException;

import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private UserService userService;

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

    public void attacheTeacher(Long subjectId, Long teacherId) throws SubjectNotFoundException, UserNotFoundException {
            Subject subject = findById(subjectId);
            User user = userService.findById(teacherId);
            subject.getTeachers().add(user);
            subjectRepository.save(subject);
    }
}
