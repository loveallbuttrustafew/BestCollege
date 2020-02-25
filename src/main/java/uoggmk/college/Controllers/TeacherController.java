package uoggmk.college.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import uoggmk.college.Services.DoneLaboratoryService;
import uoggmk.college.Services.Exceptions.LaboratoryAlreadyExistsException;
import uoggmk.college.Services.Exceptions.StorageException;
import uoggmk.college.Services.Exceptions.SubjectNotFoundException;
import uoggmk.college.Services.Exceptions.UserNotFoundException;
import uoggmk.college.Services.LaboratoryService;
import uoggmk.college.Services.StorageService;
import uoggmk.college.Services.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Controller
public class TeacherController {
    Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Value("${upload.path}")
    private String path;

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private LaboratoryService laboratoryService;

    @Autowired
    private DoneLaboratoryService doneLaboratoryService;

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

    @GetMapping("/teacher/donelaboratory/get")
    public String getDoneLaboratories(@RequestParam("subjectid") Long subjectId, Model model) {
        model.addAttribute("laboratories", doneLaboratoryService.getAllDoneLaboratories(subjectId));
        return "donelabs";
    }

    @GetMapping(value = "/teacher/download/{file_name}")
    public ResponseEntity<ByteArrayResource> downloadLaboratory(@PathVariable("file_name") String name) throws IOException {
        Path newPath = Paths.get(path + "/" + name);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(newPath));

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
