package uoggmk.college.Controllers;

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
    @Autowired
    private GroupService groupService;

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/admin/subject/add")
    private String subjectAdd(@RequestParam("groupid") Long id,
                              @RequestParam("subjectname") String name) {
        Subject subject = new Subject();
        subject.setName(name);
        try {
            subject.setGroup(groupService.findById(id));
            subjectService.add(subject);
        } catch (GroupNotFoundException | SubjectAlreadyExistsException e) {
            e.printStackTrace();
        }

        return "redirect:/admin";
    }
    }
