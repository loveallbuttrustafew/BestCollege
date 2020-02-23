package uoggmk.college.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uoggmk.college.Models.Role;
import uoggmk.college.Models.User;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Set<User> findByRole(Role role);
}
