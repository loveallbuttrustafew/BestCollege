package uoggmk.college.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uoggmk.college.Models.Role;
import uoggmk.college.Models.User;
import uoggmk.college.Services.Exceptions.UserAlreadyExistsException;
import uoggmk.college.Services.UserService;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String main() {
        return "admin";
    }

    @PostMapping("/admin/user/add")
    public String addUser(@RequestParam("typeuser") Role type, @RequestParam("firstname") String firstName,
                          @RequestParam("secondname") String secondName, @RequestParam("lastname") String lastName,
                          @RequestParam("username") String username, @RequestParam("password") String password,
                          @RequestParam("phonenumber") String phoneNumber, @RequestParam("studentbook") String studentBook){
        User user = new User();
        user.setRole(type);
        user.setEnabled(true);
        user.setFirstName(firstName);
        user.setMiddleName(secondName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setStudentsBookNumber(studentBook);

        try {
            userService.addUser(user);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }
}
