package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.Service.UserService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/user")

//Для тестов
//@RequestMapping("/")
//@RequestMapping()
public class UserController {

    /*
    private final UserService userService;


    //это не нужно
    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);

        return "hello";
    }

    @GetMapping("/login")
    public String loginPage() {
        //return "login_default";
        return "login";
    }

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "user/show_User";
    }
    */

    /*
    // На создавал кучу одинаковыъ методов это метод тот же что и show (showUser)/ Смотри вверху
    @GetMapping("/{id}")
    public String getUserInfoByName(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.show(id));
//        User user = userService.show((int) id);
//        model.addAttribute("user", user);
//        model.addAttribute("roles", user.getRoles());
        return "user/show_User";
    }

     */
}


