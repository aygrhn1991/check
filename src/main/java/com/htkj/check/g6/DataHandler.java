package com.htkj.check.g6;

import org.ht.iov.service.core.g6.EngineData;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.ht.utils.core.Tuple2;

public class DataHandler {

    public static void h(Object obj, ConfigModel configModel, List<DataModel> vins) {
        try {

            Tuple2<String, EngineData> t3 = (Tuple2<String, EngineData>) obj;
            EngineData eng = t3.getB();
            String vin = t3.a;

            long position = eng.getPositioningState();
            long time = eng.getAttm().getTime();
            double speed = eng.getEngineSpeed();
            long torque = eng.getFrictionTorque();

            double lng = eng.getLongitude();
            double lat = eng.getLatitude();
            Long l1 = System.currentTimeMillis();
            for (int i = 0; i < vins.size(); i++) {
                DataModel result = vins.get(i);
                //检查超时
                if (result.datas.size() <= 1) {
                    result.isOverTime = true;
                } else {
                    System.out.println((new Date().getTime() - result.datas.get(result.datas.size() - 1).time) / 1000);
                    result.isOverTime = (new Date().getTime() - result.datas.get(result.datas.size() - 1).time) / 1000 >= configModel.overTime;
                }
                if (result.vin.equals(vin)) {
                    //存储数据
                    DataSubModel dataSubModel = new DataSubModel();
                    dataSubModel.location = position;
                    dataSubModel.time = time;
                    dataSubModel.speed = speed;
                    dataSubModel.torque = torque;
                    result.datas.add(dataSubModel);
                    //排序（不排序了，浪费资源，耗时）
                    Collections.sort(result.datas, new DataSubModelComparator());
                    //检查发动机转速
                    result.isSpeed = speed == configModel.engineSpeed;
                    //检查定位
                    int intp = (int) position;
                    int intr = (intp >> (1 - 1)) & 1;
                    result.isLocate = intr == 1 ? true : false; //(position & 1) == 0;
                    //如果是J7，检查摩擦扭矩
                    if (configModel.dtuType == DtuType.J7) {
                        result.isTorque = torque == configModel.frictionTorque;
                    }
                    //检查时间间隔
//                    if (result.datas.size() <= 1) {
//                        result.isInterval = false;
//                    } else {
//                        result.isInterval = (time - result.datas.get(result.datas.size() - 1).time) / 1000 <= 1;
//                    }
                    //总结论
                    result.result = result.isSpeed && result.isLocate && result.isOverTime && (configModel.dtuType == DtuType.J7 ? result.isTorque : true);
                }
            }
            long l2 = System.currentTimeMillis();
            System.out.println("计算耗时：" + (l2 - l1) + ",开始：" + l1 + ",结束：" + l2);

        } catch (Exception e) {
            System.out.println(obj);
        }
    }

}
