package uoggmk.college.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uoggmk.college.Models.Group;
import uoggmk.college.Models.Role;
import uoggmk.college.Models.User;
import uoggmk.college.Services.Exceptions.GroupNotFoundException;
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

}
