package web.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.api.json.TypeResponse;
import web.service.TypeService;
import web.service.dto.TypeDto;
import web.service.exception.CustomException;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/web/types")
public class TypeWebController {
    private final TypeService typeService;

    @GetMapping(path = "/get")
    public String getAll(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") == null){
            return "redirect:/web/user/login/form";
        }
        model.addAttribute("types", typeService.getAll());
        return "type";
    }

    @PostMapping(path = "/insert")
    public String add(HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("userId") == null){
                return "redirect:/user/login/form";
            }
            typeService.insert(request.getParameter("newName"));
            return "redirect:/web/types/get";
        } catch (CustomException e){
            return "redirect:/user/login/form";
        }
    }

    @PostMapping
    public String change(@RequestParam(value = "edit", required = false) String edit,
                         @RequestParam(value = "delete", required = false) String delete,
                         HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") == null){
            return "redirect:/web/user/login/form";
        }
        if(edit != null){
            typeService.edit(request.getParameter("id"), new TypeDto(request.getParameter("name")));
        }
        if(delete != null){
            typeService.delete(request.getParameter("id"));
        }
        return "redirect:/web/types/get";
    }
}
