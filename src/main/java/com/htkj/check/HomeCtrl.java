package com.htkj.check;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeCtrl {

    @RequestMapping("/index")
    public String login() {
        return "index";
    }

}
