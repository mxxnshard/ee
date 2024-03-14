package web.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.api.json.UserListResponse;
import web.api.json.UserLoginRequest;
import web.api.json.UserRegisterRequest;
import web.service.UserService;
import web.service.dto.UserDto;
import web.service.exception.CustomException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserApiController {
    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET, path = "/user-list")
    public ResponseEntity<UserListResponse> test(@RequestParam String name) {
        return ResponseEntity.ok(new UserListResponse(userService.getUsersByFirsName(name)));
    }

    @PostMapping(path = "register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequest req) {
        boolean register = userService.register(
                new UserDto(
                        req.getFirstName(),
                        req.getSecondName().orElse(null),
                        req.getEmail()),
                req.getPass());
        return register ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "login")
    public ResponseEntity<?> login(
            @RequestBody UserLoginRequest req,
            HttpServletRequest request) {
        try {
            UserDto userDto = userService.login(req.getEmail(), req.getPass());
            HttpSession session = request.getSession();
            session.setAttribute("userId", userDto.getId());
            return ResponseEntity.ok(userDto);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("userId", null);
        return ResponseEntity.ok().build();
    }

}
