package uoggmk.college.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uoggmk.college.Models.Group;
import uoggmk.college.Models.Role;
import uoggmk.college.Models.User;
import uoggmk.college.Services.Exceptions.GroupAlreadyExistsException;
import uoggmk.college.Services.Exceptions.GroupNotFoundException;
import uoggmk.college.Services.Exceptions.SubjectNotFoundException;
import uoggmk.college.Services.Exceptions.UserNotFoundException;
import uoggmk.college.Services.GroupService;
import uoggmk.college.Services.SubjectService;
import uoggmk.college.Services.UserService;

import java.util.List;
import java.util.Set;

@RestController
public class AdminRestController {
    Logger logger = LoggerFactory.getLogger(AdminRestController.class);

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/admin/group/getall")
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/admin/group/get")
    public Group getGroupById(@RequestParam("groupid") Long id){
        try {
            return groupService.getGroupById(id);
        } catch (GroupNotFoundException e) {
            logger.error("[EXCEPTION] GroupNotFound while get group by id (" + id + ")");
        }
        return null;
    }

    @GetMapping("/admin/users/get/teachers")
    public Set<User> getAllTeachers() {
        return userService.findByRole(Role.TEACHER);
    }

    @GetMapping("/admin/group/delete")
    public Status deleteGroup(@RequestParam Long groupId) {
        try {
            groupService.deleteById(groupId);
        } catch (GroupNotFoundException e) {
            return new Status("BAD");
        }
        return new Status("OK");
    }

    @GetMapping("/admin/user/delete")
    public Status deleteUser(@RequestParam Long userId) {
        try {
            userService.deleteById(userId);
        } catch (UserNotFoundException e) {
            return new Status("BAD");
        }
        return new Status("OK");
    }

    @GetMapping("/admin/subject/delete")
    public Status deleteSubject(@RequestParam Long subjectId) {
        try {
            subjectService.deleteById(subjectId);
        } catch (SubjectNotFoundException e) {
            return new Status("BAD");
        }
        return new Status("OK");
    }
}
