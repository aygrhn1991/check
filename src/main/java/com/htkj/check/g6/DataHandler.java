package com.htkj.check.g6;

import org.ht.iov.service.core.g6.EngineData;
import org.ht.iov.service.core.g6.ObdData;
import org.ht.utils.core.Tuple3;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DataHandler {

    public static void h(Object obj, ConfigModel configModel, List<DataModel> results) {
        Tuple3 t3 = (Tuple3) obj;
        EngineData eng = (EngineData) (t3.a);
        ObdData obd = (ObdData) (t3.b);

        String vin = obd.getVin();
        double speed = eng.getEngineSpeed();
        long time = eng.getAttm().getTime();
        double lng = eng.getLongitude();
        double lat = eng.getLatitude();
        Long l1 = System.currentTimeMillis();
        for (int i = 0; i < results.size(); i++) {
            DataModel result = results.get(i);
            //检查超时
            if (result.datas.size() <= 1) {
                result.isOverTime = true;
            } else {
                result.isOverTime = (new Date().getTime() - result.datas.get(result.datas.size() - 1).time) / 1000 >= configModel.overTime;
            }
            if (result.vin.equals(vin)) {
                //存储数据
                DataSubModel dataSubModel = new DataSubModel();
                dataSubModel.time = time;
                dataSubModel.lng = lng;
                dataSubModel.lat = lat;
                dataSubModel.speed = speed;
                result.datas.add(dataSubModel);
                //排序（不排序了，浪费资源，耗时）
                Collections.sort(result.datas, new DataSubModelComparator());
                //检查发动机转速
                result.isSpeed = speed == configModel.speed;
                //检查定位
                result.isLocate = lng != 0 && lat != 0;
                //检查时间间隔
                if (result.datas.size() <= 1) {
                    result.isInterval = false;
                } else {
                    result.isInterval = (time - result.datas.get(result.datas.size() - 1).time) / 1000 <= 1;
                }
                //总结论
                result.result = result.isSpeed && result.isLocate && result.isInterval && result.isOverTime;
            }
        }
        long l2 = System.currentTimeMillis();
        System.out.println("计算耗时：" + (l2 - l1) + ",开始：" + l1 + ",结束：" + l2);
    }
}
