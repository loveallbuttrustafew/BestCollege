package uoggmk.college.Services;

import java.util.List;
import java.util.Set;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uoggmk.college.Models.User;
import uoggmk.college.Models.Role;
import uoggmk.college.Models.Subject;
import uoggmk.college.Repositories.UserRepository;
import uoggmk.college.Services.Exceptions.UserAlreadyExistsException;
import uoggmk.college.Services.Exceptions.UserNotFoundException;

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

    public void addUser(User user) throws UserAlreadyExistsException {
        try {
            findByUsername(user.getUsername());
            throw new UserAlreadyExistsException();
        } catch (UserNotFoundException e) {
            userRepository.save(user);
        }
    }

    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public Set<Subject> getAllSubjects(Long userId) throws UserNotFoundException {
            User user = findById(userId);
            return user.getSubjects();
    }
}
