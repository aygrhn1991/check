package com.htkj.check.jly;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/jly")
public class JlyCtrl {

    @RequestMapping("/index")
    public String index() {
        return "jly/index";
    }

    @RequestMapping("/auth")
    public String auth() {
        return "jly/auth";
    }

    @RequestMapping("/checkpicture")
    public String checkpicture() {
        return "jly/checkpicture";
    }

    @RequestMapping("/createid")
    public String createid() {
        return "jly/createid";
    }


}
