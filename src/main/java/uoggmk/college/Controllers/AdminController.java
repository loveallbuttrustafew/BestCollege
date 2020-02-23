package uoggmk.college.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uoggmk.college.Models.Group;
import uoggmk.college.Models.Role;
import uoggmk.college.Models.Subject;
import uoggmk.college.Models.User;
import uoggmk.college.Services.Exceptions.*;
import uoggmk.college.Services.GroupService;
import uoggmk.college.Services.SubjectService;
import uoggmk.college.Services.UserService;

@Controller
public class AdminController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/admin")
    public String main() {
        return "admin";
    }

    @PostMapping("/admin/user/add")
    public String addUser(@RequestParam("typeuser") Role type,
                          @RequestParam("firstname") String firstName,
                          @RequestParam("secondname") String secondName,
                          @RequestParam("lastname") String lastName,
                          @RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam(value = "phonenumber", required = false) String phoneNumber,
                          @RequestParam(value = "studentbook", required = false) String studentBook,
                          @RequestParam(value = "groupid", required = false) Long groupId) {
        User.UserBuilder userBuilder = User.builder()
                .role(type)
                .enabled(true)
                .firstName(firstName)
                .middleName(secondName)
                .lastName(lastName)
                .username(username)
                .password(password);

        if (phoneNumber != null)
            userBuilder.phoneNumber(phoneNumber);

        if (studentBook != null)
            userBuilder.studentsBookNumber(studentBook);

        if (groupId != null)
            try {
                userBuilder.group(groupService.findById(groupId));
            } catch (GroupNotFoundException e) {
                logger.error("[EXCEPTION] GroupNotFound while add user (" + username + ")");
                return "redirect:/admin";
            }

        try {
            userService.addUser(userBuilder.build());
        } catch (UserAlreadyExistsException e) {
            logger.error("[EXCEPTION] UserAlreadyExists while add user (" + username + ")");
        }

        return "redirect:/admin";
    }

    @PostMapping("/admin/group/add")
    public String addGroup(@RequestParam("groupname") String name, @RequestParam("groupcourse") Byte course,
                           @RequestParam("groupnumber") Byte number) {
        Group.GroupBuilder groupBuilder = Group.builder()
                .name(name)
                .course(course)
                .number(number);

        try {
            groupService.addGroup(groupBuilder.build());
        } catch (GroupAlreadyExistsException e) {
            logger.error("[EXCEPTION] GroupAlreadyExists while add group (" + name + " " + course + " " + number + ")");
        }

        return "redirect:/admin";
    }

    @GetMapping("/admin/group/info")
    public String getGroupInfo(@RequestParam("id") Long id, Model model) {
        try {
            Group group = groupService.findById(id);
            model.addAttribute("group", group);
        } catch (GroupNotFoundException e) {
            logger.error("[EXCEPTION] GroupNotFound while get group info (" + id + ")");
        }
        return "group_template";
    }

    @GetMapping("/admin/subject/info")
    public String getSubjectInfo(@RequestParam("id") Long id, Model model) {
        try {
            Subject subject = subjectService.findById(id);
            model.addAttribute("subject", subject);
        } catch (SubjectNotFoundException e) {
            logger.error("[EXCEPTION] SubjectNotFound while get subject info (" + id + ")");
        }
        return "teacher_list";
    }

    @GetMapping("/admin/subject/attache/teacher")
    public String attacheTeacherToGroup(@RequestParam("teacherid") Long teacherId, @RequestParam("subjectid") Long subjectId) {
        try {
            subjectService.attacheTeacher(subjectId, teacherId);
        } catch (SubjectNotFoundException e) {
            logger.error("[EXCEPTION] SubjectNotFound while attache teacher to group (" + subjectId + " " + teacherId + ")");
        } catch (UserNotFoundException e) {
            logger.error("[EXCEPTION] UserNotFound while attache teacher to group (" + subjectId + " " + teacherId + ")");
        }
        return "redirect:/admin";
    }
}
