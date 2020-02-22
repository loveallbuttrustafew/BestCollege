package uoggmk.college.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uoggmk.college.Models.Group;
import uoggmk.college.Models.Role;
import uoggmk.college.Models.User;
import uoggmk.college.Services.GroupService;
import uoggmk.college.Services.UserService;
import uoggmk.college.Services.Exceptions.GroupNotFoundException;

@RestController
public class AdminRestController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    @GetMapping("/admin/group/getall")
    public List<Group> getall(){
        return groupService.getAllGroups();
    }

    @GetMapping("/admin/group/get")
    public Group groupAdd(@RequestParam("groupid") Long id) throws GroupNotFoundException {
        return groupService.getGroupById(id);
    }

    @GetMapping("/admin/users/get/teachers")
    public List<User> getTeachers() {
        return userService.findByRole(Role.TEACHER);
    }
}
