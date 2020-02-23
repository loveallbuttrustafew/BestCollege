package uoggmk.college.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uoggmk.college.Models.Subject;
import uoggmk.college.Services.Exceptions.GroupNotFoundException;
import uoggmk.college.Services.Exceptions.SubjectAlreadyExistsException;
import uoggmk.college.Services.GroupService;
import uoggmk.college.Services.SubjectService;

@Controller
public class SubjectController {
    Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private GroupService groupService;

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/admin/subject/add")
    private String createSubject(@RequestParam("groupid") Long id,
                              @RequestParam("subjectname") String name) {
        Subject subject = new Subject();
        subject.setName(name);
        try {
            subject.setGroup(groupService.findById(id));
            subjectService.add(subject);
        } catch (GroupNotFoundException e) {
            logger.error("[EXCEPTION] GroupNotFound while create subject (" + id + " " + name + ")");
        } catch (SubjectAlreadyExistsException e) {
            logger.error("[EXCEPTION] SubjectAlreadyExists while create subject (" + id + " " + name + ")");
        }

        return "redirect:/admin";
    }
}
