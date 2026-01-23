package poly.edu.assignment.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportAController {

    @GetMapping("/admin/reports")
    public String reports() {
        return "admin/report";
    }
}
