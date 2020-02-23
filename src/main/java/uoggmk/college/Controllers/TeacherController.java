package uoggmk.college.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uoggmk.college.Models.Subject;
import uoggmk.college.Services.Exceptions.UserNotFoundException;
import uoggmk.college.Services.UserService;

import java.util.Set;

@Controller
public class TeacherController {
    @Autowired
    private UserService userService;

    @GetMapping("/teacher")
    public String main(Model model) {
        try {
            {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String username = auth.getName();
                Set<Subject> subjects = userService.getAllSubjects(userService.findByUsername(username).getId());
                model.addAttribute("subjects", subjects);
                return "teacher";
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
