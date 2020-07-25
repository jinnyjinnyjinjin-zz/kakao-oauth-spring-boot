package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("oauth")
public class AuthController {

    /**
     * 인증 코드 받음
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("login")
    public String code(Model model, HttpServletRequest request) {
        String code = request.getQueryString();
        model.addAttribute("code", code);
        return "code";
    }
}
