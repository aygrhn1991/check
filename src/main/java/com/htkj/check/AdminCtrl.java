package com.htkj.check;

import com.htkj.check.workshop.DtuMsgHandle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminCtrl {

    private List<DataModel> ldm=null;

    @RequestMapping("/index")
    public String index() {
        return "/index";
    }

    @RequestMapping("/check")
    public String check() {
        return "/check";
    }


    @RequestMapping("/startCheck")
    @ResponseBody
    public boolean startCheck(@RequestBody ConfigModel configModel) {
        ldm = new ArrayList<>();
        for (String vin : configModel.vins) {
            DataModel dataModel = new DataModel();
            dataModel.vin = vin;
            dataModel.datas=new ArrayList<>();
            ldm.add(dataModel);
        }
        DtuMsgHandle.results = ldm;
        DtuMsgHandle.on = true;
        DtuMsgHandle.config = configModel;
        return true;
    }

    @RequestMapping("/stopCheck")
    @ResponseBody
    public boolean stopCheck() {
        DtuMsgHandle.on = false;
        return true;
    }

    @RequestMapping("/onLine")
    @ResponseBody
    public List<DataModel> onLine() {
        return ldm;
    }
}
