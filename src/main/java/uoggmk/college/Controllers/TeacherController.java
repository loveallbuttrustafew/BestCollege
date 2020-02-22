package uoggmk.college.Controllers;

import java.util.Set;

import uoggmk.college.Models.User;
import uoggmk.college.Models.Subject;
import uoggmk.college.Services.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeacherController {
    @Autowired
    private UserService userService;

    @GetMapping("/teacher")
    public String main(@AuthenticationPrincipal User user, Model model) {
        Set<Subject> subjects = userService.getAllSubjects(user.getId());
        model.addAttribute("subjects",subjects);
        return "teacher";
    }
}
