package com.htkj.check.jly;

import com.htkj.check.DataModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


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

    @RequestMapping("/getDevices/{pageIndex}/{pageSize}/{vehNo}/{dtuNo}/{simNo}")
    @ResponseBody
    public DataModel getTestData(@PathVariable("pageIndex") int pageIndex,
                                 @PathVariable("pageSize") int pageSize,
                                 @PathVariable("vehNo") String vehNo,
                                 @PathVariable("dtuNo") String dtuNo,
                                 @PathVariable("simNo") String simNo) {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            Map map = new HashMap();
            map.put("aaa", String.valueOf(new Date().getTime()));
            map.put("bbb", "bbb" + i);
            map.put("ccc", "ccc" + i);
            list.add(map);
        }
        return new DataModel(1000, list);
    }

    @RequestMapping("/unBind/{dtuNo}")
    @ResponseBody
    public boolean unBind(@PathVariable("dtuNo") String dtuNo) {
        return true;
    }

    @RequestMapping("/getOnlineDevices")
    @ResponseBody
    public DataModel getOnlineDevices() {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map map = new HashMap();
            map.put("id", i);
            map.put("name", "name" + i);
            list.add(map);
        }
        return new DataModel(list.size(), list);
    }

    @RequestMapping("/takePicture/{dtuNo}")
    @ResponseBody
    public boolean takePicture(@PathVariable("dtuNo") String dtuNo) {
        return true;
    }

    @RequestMapping("/getPictureData/{dtuNo}")
    @ResponseBody
    public DataModel getPictureData(@PathVariable("dtuNo") String dtuNo) {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map map = new HashMap();
            map.put("aaa", String.valueOf(new Date().getTime()));
            map.put("bbb", "bbb" + i);
            map.put("ccc", "ccc" + i);
            list.add(map);
        }
        return new DataModel(list.size(), list);
    }

    @RequestMapping("/getDeviceType")
    @ResponseBody
    public DataModel getDeviceType() {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map map = new HashMap();
            map.put("id", i);
            map.put("type", "type" + i);
            list.add(map);
        }
        return new DataModel(list.size(), list);
    }

    @RequestMapping("/getPositon")
    @ResponseBody
    public int getPositon() {
        return 10086;
    }

    @RequestMapping("/createIds/{date}/{type}/{subType}/{c3}/{positon}/{count}")
    @ResponseBody
    public DataModel createIds(@PathVariable("date") long date,
                               @PathVariable("type") String type,
                               @PathVariable("subType") String subType,
                               @PathVariable("c3") String c3,
                               @PathVariable("positon") int positon,
                               @PathVariable("count") int count) {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = positon + 1; i < positon + 1 + count; i++) {
            Map map = new HashMap();
            map.put("id", i);
            map.put("name", "name" + i);
            list.add(map);
        }
        return new DataModel(list.size(), list);
    }

    @RequestMapping("/exportIds")
    @ResponseBody
    public int exportIds() {
        return 10086;
    }
}
