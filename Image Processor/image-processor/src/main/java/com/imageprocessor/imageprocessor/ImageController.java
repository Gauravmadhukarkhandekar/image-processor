package com.imageprocessor.imageprocessor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}