
package poly.edu.assignment.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountAController {
    @GetMapping("/admin/accounts")
    public String accounts() {
        return "admin/account";
    }
}