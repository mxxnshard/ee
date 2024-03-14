package web.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.api.json.UserListResponse;
import web.service.UserService;
import web.service.dto.UserDto;
import web.service.exception.CustomException;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/web/user")
public class UserWebApiController {
    private final UserService userService;

    @GetMapping("login/form")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user-list")
    public ResponseEntity<UserListResponse> test(@RequestParam String name) {
        return ResponseEntity.ok(new UserListResponse(userService.getUsersByFirsName(name)));
    }

    @PostMapping(path = "register")
    public String register(HttpServletRequest request) {
        boolean register = userService.register(
                new UserDto(
                        request.getParameter("firstName"),
                        request.getParameter("secondName"),
                        request.getParameter("email")),
                        request.getParameter("pass"));
        return "redirect:/";
    }

    @GetMapping(path = "register")
    public String reg(){
        return "register";
    }
    @PostMapping(path = "login")
    public String login(
            HttpServletRequest request) {
        try {
            System.out.println(request.getParameter("email"));
            UserDto userDto = userService.login(request.getParameter("email"), request.getParameter("password"));
            System.out.println(userDto.getId());
            HttpSession session = request.getSession();
            session.setAttribute("userId", userDto.getId());
            return "redirect:/web/user/admin";
        } catch (CustomException e) {
            return "redirect:/web/user/login/form";
        }
    }

    @GetMapping("/admin")
    public String admin(Model model){
        return "admin";
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("userId", null);
        return ResponseEntity.ok().build();
    }

}
