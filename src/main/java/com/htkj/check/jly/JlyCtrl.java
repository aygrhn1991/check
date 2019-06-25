package com.htkj.check.jly;

import com.htkj.check.g6.ConfigModel;
import com.htkj.check.g6.DataModel;
import com.htkj.check.g6.workshop.DtuMsgHandle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @RequestMapping("/getDevices/{vehno}/{dtuno}/{simno}")
    @ResponseBody
    public List<Map<String, String>> getTestData(@PathVariable("vehno") String vehno,
                                                 @PathVariable("dtuno") String dtuno,
                                                 @PathVariable("simno") String simno) {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Map map = new HashMap();
            map.put("aaa", "aaa" + i);
            map.put("bbb", "bbb" + i);
            map.put("ccc", "ccc" + i);
            list.add(map);
        }
        return list;
    }

    @RequestMapping("/unBind/{vehno}")
    @ResponseBody
    public boolean getTestData(@PathVariable("vehno") String vehno) {
        return true;
    }


}
