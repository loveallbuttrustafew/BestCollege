package uoggmk.college.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uoggmk.college.Models.Role;
import uoggmk.college.Models.Subject;
import uoggmk.college.Models.User;
import uoggmk.college.Repositories.UserRepository;
import uoggmk.college.Services.Exceptions.UserAlreadyExistsException;
import uoggmk.college.Services.Exceptions.UserNotFoundException;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UserNotFoundException();
        return user;
    }

    public User findById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException();
        return user.get();
    }

    public void deleteById(Long id) throws UserNotFoundException {
        findById(id);
        userRepository.deleteById(id);
    }

    public void addUser(User user) throws UserAlreadyExistsException {
        try {
            findByUsername(user.getUsername());
            throw new UserAlreadyExistsException();
        } catch (UserNotFoundException e) {
            userRepository.save(user);
        }
    }

    public Set<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public Set<Subject> getAllSubjects(Long userId) throws UserNotFoundException {
        User user = findById(userId);
        return user.getSubjects();
    }

    public Set<Subject> getAllSubjectFromGroup(Long userId) throws UserNotFoundException {
        User user = findById(userId);
        return user.getGroup().getSubjects();
    }
}
