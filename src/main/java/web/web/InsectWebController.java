package web.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.service.InsectService;
import web.service.TypeService;
import web.service.dto.InsectDto;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/web/insects")
public class InsectWebController {
    private final InsectService service;
    private final TypeService typeService;

    @PostMapping(path = "insert")
    public String add(
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/web/user/login/form";
        }

        InsectDto dto = new InsectDto(userId, request.getParameter("newName"), Integer.parseInt(request.getParameter("newSize")));
        String id = service.insert(dto);
        return "redirect:/web/insects/get";
    }

    @GetMapping(path = "/get")
    public String getAll(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/web/user/login/form";
        }
        model.addAttribute("insects", service.getAll());
        model.addAttribute("types", typeService.getAll());
        return "insect";
    }

    @PostMapping
    public String insects(@RequestParam(value = "edit", required = false) String edit,
                            @RequestParam(value = "delete", required = false) String delete,
                            HttpServletRequest request, HttpSession session ) {
        session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/web/user/login/form";
        }
        if(edit != null){
            service.edit(request.getParameter("id"), new InsectDto(userId, request.getParameter("name"), Integer.parseInt(request.getParameter("size"))));
        }
        if(delete != null){
            service.delete(request.getParameter("id"));
        }

        return "redirect:/web/insects/get";
    }
}
