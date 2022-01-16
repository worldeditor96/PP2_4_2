package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.Service.RoleService;
import web.Service.UserService;
import web.models.User;


@Controller
//Для тестов
@RequestMapping("/admin")
//@RequestMapping("/")
//@RequestMapping()
public class AdminController {


    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /*
    // корзина товаров//////////////////////////////////////////////////////
    private final UserServiceRepository userServiceRepository;

    @Autowired
    public AdminController(UserServiceRepository userServiceRepository){
        this.userServiceRepository = userServiceRepository;
    }
    */

// кажется нужно убрать это нужно было в примере для регистрации


    // корзина товаров///////////////////////////////////////////////////////

    //это не нужно
//    @RequestMapping(value = "hello", method = RequestMethod.GET)
//    public String printWelcome(ModelMap model) {
//        List<String> messages = new ArrayList<>();
//        messages.add("Hello!");
//        messages.add("I'm Spring MVC-SECURITY application");
//        messages.add("5.2.0 version by sep'19 ");
//        model.addAttribute("messages", messages);
//
//        return "hello";
//    }
//
//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }

//РАБОТАТЕ
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", userService.index());
        return "index";
    }

    @GetMapping("/admin/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "admin/show";
    }

    @GetMapping("/{id}")
    public String getUserInfoByName(@PathVariable("id") int id, Model model) {
        User user = userService.show(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "admin/showForAdmin";
    }

//    @GetMapping("/new")
//    public String newPerson(Model model){
//        model.addAttribute("user", new User());
//        return "admin/new";
//    }
    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/new";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("user") User user){
        userService.save(user);
        return "redirect:/index";
    }

    @GetMapping("{id}/edit")
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

    @PatchMapping("{id}")
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
