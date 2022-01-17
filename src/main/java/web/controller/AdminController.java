package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.Service.RoleService;
import web.Service.UserService;
import web.models.User;


@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


//РАБОТАТЕ+
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", userService.index());
        return "admin/index";
    }

    //+РАБОТАТЕ
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "admin/show";
    }


    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("user", new User());
        return "admin/new";
    }


    @PostMapping("/")
    public String create(@ModelAttribute("user") User user){
        userService.save(user);
        return "admin/index";
    }


    @GetMapping("admin/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.show(id));
        return "admin/edit";

    }

//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") long id) {
//        User user = userService.getUser(id);
//        model.addAttribute("user", user);
//        model.addAttribute("roles", roleService.getAllRoles());
//        return "admin/edit";
//    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.update(id, user);
        return "redirect:/admin/index";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin/index";
    }

}
