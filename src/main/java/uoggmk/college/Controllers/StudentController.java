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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uoggmk.college.Models.Subject;
import uoggmk.college.Services.Exceptions.LaboratoryAlreadyExistsException;
import uoggmk.college.Services.Exceptions.StorageException;
import uoggmk.college.Services.Exceptions.SubjectNotFoundException;
import uoggmk.college.Services.Exceptions.UserNotFoundException;
import uoggmk.college.Services.LaboratoryService;
import uoggmk.college.Services.StorageService;
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

    @Autowired
    private StorageService storageService;

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

    @PostMapping(value = "/student/upload/laboratory", consumes = {"multipart/form-data"})
    public String uploadLaboratory(@RequestParam("file") MultipartFile file, @RequestParam("subjectid") Long subjectId, Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            storageService.uploadDoneLaboratory(file, subjectId, userService.findByUsername(username));
        } catch (StorageException e) {
            logger.error("[EXCEPTION] StorageException while upload done file (" + file.getOriginalFilename() + subjectId + ")");
        } catch (SubjectNotFoundException e) {
            logger.error("[EXCEPTION] SubjectNotFound while upload done file (" + file.getOriginalFilename() + subjectId + ")");
        } catch (LaboratoryAlreadyExistsException e) {
            logger.error("[EXCEPTION] LaboratoryAlreadyExists while upload done file (" + file.getOriginalFilename() + subjectId + ")");
        } catch (UserNotFoundException e) {
            logger.error("[EXCEPTION] UserNotFound while upload done file (" + file.getOriginalFilename() + subjectId + ")");
        }
        return "redirect:/student";
    }
}
