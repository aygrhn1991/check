package com.htkj.check;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminCtrl {

    @RequestMapping("/index")
    public String index() {
        return "/index";
    }

    @RequestMapping("/check")
    public String check() {
        return "/check";
    }

}
