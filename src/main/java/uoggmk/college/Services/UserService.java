package uoggmk.college.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uoggmk.college.Repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}
