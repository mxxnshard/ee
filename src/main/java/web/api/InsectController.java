package web.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.api.json.InsectResponse;
import web.service.InsectService;
import web.service.dto.InsectDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/insects")
public class InsectController {
    private final InsectService service;

    @PostMapping(path = "insert/{name}/{size}")
    public ResponseEntity<String> add(
            @PathVariable String name,
            @PathVariable Integer size,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Залогиньтесь");
        }

        InsectDto dto = new InsectDto(userId, name, size);
        String id = service.insert(dto);
        return ResponseEntity.ok(id);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<?> getAll(HttpServletRequest request){
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Залогиньтесь");
        }
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping(path = "/delete")
    public ResponseEntity<?> delete(HttpServletRequest request){
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Залогиньтесь");
        }
        service.delete(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/edit")
    public ResponseEntity<?> edit(@RequestParam String id, @RequestBody InsectResponse response, HttpServletRequest request){
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Залогиньтесь");
        }
        service.edit(userId, new InsectDto(userId, response.getName(), response.getSize()));
        return ResponseEntity.ok().build();
    }
}
