package pl.coderslab.charity.controller.admin;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminPanelController {
    @GetMapping
    public String showAdminPanelDashboard() {
        return "admin/index";
    }
}
