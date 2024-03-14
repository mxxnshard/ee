package web.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.api.json.TypeResponse;
import web.service.TypeService;
import web.service.dto.TypeDto;
import web.service.exception.CustomException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/types")
public class TypeController {
    private final TypeService typeService;

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(typeService.getAll());
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> add(@RequestBody TypeResponse type, HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("userId") == null){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Залогиньтесь");
            }
            typeService.insert(type.getName());
            return ResponseEntity.ok().build();
        } catch (CustomException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/edit")
    public ResponseEntity<?> edit(@RequestParam String id, @RequestBody TypeResponse type, HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            String userId = (String) session.getAttribute("userId");
            if(userId == null){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Залогиньтесь");
            }
            typeService.edit(id, new TypeDto(type.getName()));
            return ResponseEntity.ok().build();
        } catch (CustomException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/delete")
    public ResponseEntity<?> delete(@RequestParam String name, HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("userId") == null){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Залогиньтесь");
            }
            typeService.delete(name);
            return ResponseEntity.ok().build();
        } catch (CustomException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
