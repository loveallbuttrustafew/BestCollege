package uoggmk.college.Controllers;

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
import uoggmk.college.Services.GroupService;
import uoggmk.college.Services.SubjectService;
import uoggmk.college.Services.UserService;
import uoggmk.college.Services.Exceptions.GroupAlreadyExistsException;
import uoggmk.college.Services.Exceptions.GroupNotFoundException;
import uoggmk.college.Services.Exceptions.SubjectNotFoundException;
import uoggmk.college.Services.Exceptions.UserAlreadyExistsException;

@Controller
public class AdminController {
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
        User user = new User();
        user.setRole(type);
        user.setEnabled(true);
        user.setFirstName(firstName);
        user.setMiddleName(secondName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);

        if (phoneNumber != null)
            user.setPhoneNumber(phoneNumber);

        if (studentBook != null)
            user.setStudentsBookNumber(studentBook);

        if (groupId != null) {
            try {
                user.setGroup(groupService.findById(groupId));
            } catch (GroupNotFoundException e) {
                e.printStackTrace();
                return "redirect:/admin";
            }
        }

        try {
            userService.addUser(user);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
        }

        return "redirect:/admin";
    }

    @PostMapping("/admin/group/add")
    public String groupAdd(@RequestParam("groupname") String name, @RequestParam("groupcourse") Byte course,
                           @RequestParam("groupnumber") Byte number) {
        Group newGroup = new Group();
        newGroup.setName(name);
        newGroup.setCourse(course);
        newGroup.setNumber(number);

        try {
            groupService.addGroup(newGroup);
        } catch (GroupAlreadyExistsException e) {
            e.printStackTrace();
        }

        return "redirect:/admin";
    }

    @GetMapping("/admin/group/info")
    public String groupAdd(@RequestParam("id") Long id, Model model) {
        try {
            Group group = groupService.findById(id);
            model.addAttribute("group", group);
        } catch (GroupNotFoundException e) {
            e.printStackTrace();
        }
        return "group_template";
    }

    @GetMapping("/admin/subject/info")
    public String subjectInfo(@RequestParam("id") Long id, Model model) {
        try {
            Subject subject = subjectService.findById(id);
            model.addAttribute("subject", subject);
        } catch (SubjectNotFoundException e) {
            e.printStackTrace();
        }
        // TODO change subject template
        return "";
    }
}
