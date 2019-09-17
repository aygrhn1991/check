package com.htkj.check.g6;

import com.htkj.check.g6.workshop.DtuMsgHandle;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/g6")
public class G6Ctrl {

    private ConfigModel configModel;

    private List<DataModel> vins = null;

    @RequestMapping("/index")
    public String index() {
        return "g6/index";
    }

    @RequestMapping("/check")
    public String check() {
        return "g6/check";
    }


    @RequestMapping("/startCheck")
    @ResponseBody
    public boolean startCheck(@RequestBody ConfigModel cm) {
        configModel = cm;
        vins = new ArrayList<>();
        for (String vin : configModel.vins) {
            DataModel dataModel = new DataModel();
            dataModel.vin = vin;
            dataModel.isOverTime = true;
            dataModel.datas = new ArrayList<>();
            vins.add(dataModel);
        }
        DtuMsgHandle.vins = vins;
        DtuMsgHandle.config = configModel;
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
        return vins;
    }


    @Scheduled(fixedDelay = 5000)
    public void checkOverTime() {
        if (DtuMsgHandle.on) {
            System.out.println("执行超时检测");
            for (int i = 0; i < vins.size(); i++) {
                DataModel result = vins.get(i);
                if (result.datas.size() <= 1) {
                    result.isOverTime = true;
                } else {
                    result.isOverTime = (new Date().getTime() - result.datas.get(result.datas.size() - 1).time) / 1000 >= configModel.overTime;
                }
            }
        }
    }
}
