package uoggmk.college.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import uoggmk.college.Models.Subject;
import uoggmk.college.Services.Exceptions.UserNotFoundException;
import uoggmk.college.Services.LaboratoryService;
import uoggmk.college.Services.UserService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;

@Controller
public class StudentController {
    Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Value("${upload.path}")
    private String path;

    @Autowired
    private UserService userService;

    @Autowired
    private LaboratoryService laboratoryService;

    @GetMapping("/student")
    public String main(Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            Set<Subject> subjects = userService.getAllSubjectFromGroup(userService.findByUsername(username).getId());
            model.addAttribute("subjects", subjects);
        } catch (UserNotFoundException e) {
            logger.error("[EXCEPTION] UserNotFound while process students's main page");
        }
        return "student";
    }

    @GetMapping("/student/laboratory/get")
    public String getLaboratory(@RequestParam("subjectid") Long subjectId, Model model) {
        model.addAttribute("laboratories", laboratoryService.getAllLaboratories(subjectId));
        return "labs_student";
    }

    @GetMapping(value = "/student/download/{file_name}")
    public InputStreamResource downloadLaboratory(@PathVariable("file_name") String name) throws FileNotFoundException {
        return new InputStreamResource(new FileInputStream(path + '/' + name));
    }
}
