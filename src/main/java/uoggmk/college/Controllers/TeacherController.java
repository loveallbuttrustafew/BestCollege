package uoggmk.college.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/teacher")
    public String main(Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            Set<Subject> subjects = userService.getAllSubjects(userService.findByUsername(username).getId());
            model.addAttribute("subjects", subjects);
        } catch (UserNotFoundException e) {
            logger.error("[EXCEPTION] UserNotFound while process teacher's main page");
        }
        return "teacher";
    }
}
