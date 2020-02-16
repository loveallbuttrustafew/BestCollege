package uoggmk.college.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uoggmk.college.Models.Group;
import uoggmk.college.Services.Exceptions.GroupNotFoundException;
import uoggmk.college.Services.GroupService;

import java.util.List;

@RestController
public class AdminRestController {
    @Autowired
    private GroupService groupService;

    @GetMapping("/admin/group/getall")
    public List<Group> getall(){
        return groupService.getAllGroups();
    }

    @GetMapping("/admin/group/get")
    public Group groupAdd(@RequestParam("groupid") Long id) throws GroupNotFoundException {
        return groupService.getGroupById(id);
    }
}