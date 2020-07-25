package com.example.demo.controller;

import com.example.demo.config.ApiInfoConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class MainController {

    private final String redirectUri;

    private final String appKey;

    public MainController(ApiInfoConfig apiInfoConfig) {
        this.redirectUri = apiInfoConfig.getRedirectUri();
        this.appKey = apiInfoConfig.getAppKey();
    }

    @RequestMapping("")
    public String index(Model model) {
        log.info("redirectUri: {}, appKey: {}", redirectUri, appKey);
        model.addAttribute("redirectUri", redirectUri);
        model.addAttribute("appKey", appKey);
        return "index";
    }
}
