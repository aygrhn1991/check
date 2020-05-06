package com.htkj.check.g6;

import com.htkj.check.g6.workshop.DtuMsgHandle;
import com.htkj.check.g6.zmq.FlowCounter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@RequestMapping("/g6")
public class G6Ctrl {

    @Value("${configFile}")
    private String configFile;

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
            dataModel.isInTime = true;
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


    @RequestMapping("/getConfigs")
    @ResponseBody
    public Map getConfigs() {
        Map config = new HashMap<>();
        Map j6 = new HashMap<>();
        Map j6_gas = new HashMap<>();
        Map j7 = new HashMap<>();
        PropertiesUtil.init(configFile);
        String vins = PropertiesUtil.get("vins");
        j6.put("inTime", PropertiesUtil.get("j6.inTime"));
        j6.put("engineSpeed", PropertiesUtil.get("j6.engineSpeed"));
        j6_gas.put("inTime", PropertiesUtil.get("j6.gas.inTime"));
        j6_gas.put("engineSpeed", PropertiesUtil.get("j6.gas.engineSpeed"));
        j6_gas.put("tankLevel", PropertiesUtil.get("j6.gas.tankLevel"));
        j7.put("inTime", PropertiesUtil.get("j7.inTime"));
        j7.put("engineSpeed", PropertiesUtil.get("j7.engineSpeed"));
        j7.put("frictionTorque", PropertiesUtil.get("j7.frictionTorque"));
        config.put("vins", vins);
        config.put("j6", j6);
        config.put("j6_gas", j6_gas);
        config.put("j7", j7);
        return config;
    }

    @RequestMapping("/saveConfig")
    @ResponseBody
    public boolean saveConfig(@RequestBody ConfigModel config) {
        PropertiesUtil.init(configFile);
        String vins = "";
        for (String vin : config.vins) {
            vins += vin + ",";
        }
        PropertiesUtil.update("vins", vins);
        if (config.dtuType == DtuType.J6) {
            PropertiesUtil.update("j6.inTime", String.valueOf(config.inTime));
            PropertiesUtil.update("j6.engineSpeed", String.valueOf(config.engineSpeed));
        }
        if (config.dtuType == DtuType.J6_GAS) {
            PropertiesUtil.update("j6.gas.inTime", String.valueOf(config.inTime));
            PropertiesUtil.update("j6.gas.engineSpeed", String.valueOf(config.engineSpeed));
            PropertiesUtil.update("j6.gas.tankLevel", String.valueOf(config.tankLevel));
        }
        if (config.dtuType == DtuType.J7) {
            PropertiesUtil.update("j7.inTime", String.valueOf(config.inTime));
            PropertiesUtil.update("j7.engineSpeed", String.valueOf(config.engineSpeed));
            PropertiesUtil.update("j7.frictionTorque", String.valueOf(config.frictionTorque));
        }
        return true;
    }

    @Scheduled(fixedDelay = 5000)
    public void checkInTime() {

        Logger.getLogger(G6Ctrl.class.getName()).log(Level.INFO, "checkInTime_out");
        if (DtuMsgHandle.on) {
            Logger.getLogger(G6Ctrl.class.getName()).log(Level.INFO, "checkInTime_on");
            System.out.println("执行超时检测");
            for (int i = 0; i < vins.size(); i++) {
                DataModel result = vins.get(i);
                if (result.datas.size() <= 1) {
                    result.isInTime = true;
                } else {
                    result.isInTime = (new Date().getTime() - result.datas.get(result.datas.size() - 1).time) / 1000 <= configModel.inTime;
                }
            }
        }
    }
}
