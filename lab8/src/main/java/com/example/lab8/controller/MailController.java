package com.example.lab8.controller;

import com.example.lab8.service.MailService;
import com.example.lab8.service.MailService.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mail")
public class MailController {

    @Autowired
    MailService mailService;

    @GetMapping("/form")
    public String showForm() {
        return "mail/form";
    }

    @PostMapping("/send-direct")
    public String sendDirect(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(required = false) String cc,
            @RequestParam(required = false) String bcc,
            @RequestParam String subject,
            @RequestParam String body,
            @RequestParam(required = false) String filenames,
            Model model) {

        Mail mail = Mail.builder()
                .from(from)
                .to(to)
                .cc(cc)
                .bcc(bcc)
                .subject(subject)
                .body(body)
                .filenames(filenames)
                .build();

        try {
            mailService.send(mail);
            model.addAttribute("message", "Email đã được gửi trực tiếp thành công!");
        } catch (Exception e) {
            model.addAttribute("message", "Lỗi gửi email: " + e.getMessage());
        }
        return "mail/result";
    }

    @PostMapping("/push-queue")
    public String pushToQueue(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(required = false) String cc,
            @RequestParam(required = false) String bcc,
            @RequestParam String subject,
            @RequestParam String body,
            @RequestParam(required = false) String filenames,
            Model model) {

        Mail mail = Mail.builder()
                .from(from)
                .to(to)
                .cc(cc)
                .bcc(bcc)
                .subject(subject)
                .body(body)
                .filenames(filenames)
                .build();

        mailService.push(mail);
        model.addAttribute("message", "Email đã được xếp vào hàng đợi. Sẽ được gửi sau.");
        return "mail/result";
    }
}