package uoggmk.college.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.management.FileSystem;
import uoggmk.college.Models.Subject;
import uoggmk.college.Services.Exceptions.LaboratoryAlreadyExistsException;
import uoggmk.college.Services.Exceptions.StorageException;
import uoggmk.college.Services.Exceptions.SubjectNotFoundException;
import uoggmk.college.Services.Exceptions.UserNotFoundException;
import uoggmk.college.Services.LaboratoryService;
import uoggmk.college.Services.StorageService;
import uoggmk.college.Services.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.*;
import java.util.Set;

@Controller
public class TeacherController {
    Logger logger = LoggerFactory.getLogger(TeacherController.class);


    private String path;

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private LaboratoryService laboratoryService;

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

    @PostMapping(value = "/teacher/upload/laboratory", consumes = {"multipart/form-data"})
    public String uploadLaboratory(@RequestParam("file") MultipartFile file, @RequestParam("subjectid") Long subjectId, Model model) {
        try {
            storageService.uploadLaboratory(file, subjectId);
        } catch (StorageException e) {
            logger.error("[EXCEPTION] StorageException while upload file (" + file.getOriginalFilename() + subjectId + ")");
        } catch (SubjectNotFoundException e) {
            logger.error("[EXCEPTION] SubjectNotFound while upload file (" + file.getOriginalFilename() + subjectId + ")");
        } catch (LaboratoryAlreadyExistsException e) {
            logger.error("[EXCEPTION] LaboratoryAlreadyExists while upload file (" + file.getOriginalFilename() + subjectId + ")");
        }
        return "redirect:/teacher";
    }

    @GetMapping("/teacher/laboratory/get")
    public String getLaboratory(@RequestParam("subjectid") Long subjectId, Model model) {
        model.addAttribute("laboratories", laboratoryService.getAllLaboratories(subjectId));
        return "labs";
    }

    @GetMapping(value = "/teacher/download/{file_name}")
    public InputStreamResource downloadLaboratory(@PathVariable("file_name") String name) throws FileNotFoundException {
        return new InputStreamResource(new FileInputStream(path + '/' + name));
    }
}
