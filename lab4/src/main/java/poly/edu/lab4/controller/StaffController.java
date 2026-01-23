
package poly.edu.lab4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import poly.edu.lab4.model.Staff;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @GetMapping("/create/form")
    public String createForm(Model model,
            @ModelAttribute("staff") Staff staff) {
        model.addAttribute("message",
                "Vui lòng nhập thông tin nhân viên!");
        return "/staff/staff-create";
    }

    @PostMapping("/create/save")
    public String createSave(Model model,
            @RequestPart("photo_file") MultipartFile photoFile,
            @Valid @ModelAttribute("staff") Staff staff,
            Errors errors) {

        if (!photoFile.isEmpty()) {
            staff.setPhoto(photoFile.getOriginalFilename());
        }

        if (errors.hasErrors()) {
            model.addAttribute("message",
                    "Vui lòng sửa các lỗi sau!");
            return "/staff/staff-create";
        } else {
            model.addAttribute("message",
                    "Dữ liệu đã nhập đúng!");
        }
        return "/staff/staff-validate";
    }

    @GetMapping("/home/index")
    public String index() {
        return "/home/index";
    }

    @GetMapping("/home/about")
    public String about() {
        return "/home/about";
    }

}
