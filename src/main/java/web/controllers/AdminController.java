package web.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dto.UserDto;
import web.service.ServiceAbstr;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ServiceAbstr<UserDto> serviceAbstr;

    public AdminController(ServiceAbstr<UserDto> serviceAbstr) {
        this.serviceAbstr = serviceAbstr;
    }

    @GetMapping
    public String getAllUsers(Authentication authentication, Model model) {
        model.addAttribute("user", serviceAbstr.getEntityByName(authentication.getName()));
        model.addAttribute("userList", serviceAbstr.allEntity());
        return "table";
    }

    @PostMapping("/userAdd")
    public String addUser(UserDto userDto) {
        serviceAbstr.addEntity(userDto);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        serviceAbstr.deleteEntity(id);
        return "redirect:/admin";
    }

    @PostMapping("/editUser")
    public String editUser(@ModelAttribute("user") UserDto userDto) {
        serviceAbstr.updateEntity(userDto);
        return "redirect:/admin";
    }
}
