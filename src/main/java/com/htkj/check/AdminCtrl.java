package com.htkj.check;

import com.htkj.check.workshop.DtuMsgHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminCtrl {

    private List<DataModel> ldm = null;

    @Autowired
    ConfigModel configModel;

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
    public boolean startCheck(@RequestBody ConfigModel cm) {
        configModel=cm;
        ldm = new ArrayList<>();
        for (String vin : configModel.vins) {
            DataModel dataModel = new DataModel();
            dataModel.vin = vin;
            dataModel.isOverTime = true;
            dataModel.datas = new ArrayList<>();
            ldm.add(dataModel);
        }
        DtuMsgHandle.results = ldm;
        DtuMsgHandle.on = true;
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


//    @Scheduled(fixedDelay = 10000)
//    public void checkOverTime() {
//        if(DtuMsgHandle.on){
//            for (int i = 0; i < ldm.size(); i++) {
//                DataModel result = ldm.get(i);
//                if (result.datas.size() <= 1) {
//                    result.isOverTime = true;
//                } else {
//                    result.isOverTime = (new Date().getTime() - result.datas.get(result.datas.size() - 1).time) / 1000 >= configModel.overTime;
//                }
//            }
//        }
//    }
}
