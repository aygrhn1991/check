package com.htkj.check;

import org.ht.iov.service.core.g6.EngineData;
import org.ht.iov.service.core.g6.ObdData;
import org.ht.utils.core.Tuple3;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DataHandler {


    public static void h(Object obj, ConfigModel config, List<DataModel> results) {
        Tuple3 t3 = (Tuple3) obj;
        EngineData eng = (EngineData) (t3.a);
        ObdData obd = (ObdData) (t3.b);

        String vin = obd.getVin();
        double speed = eng.getEngineSpeed();
        long time = eng.getAttm().getTime();
        double lng = eng.getLongitude();
        double lat = eng.getLatitude();

        for (int i = 0; i < results.size(); i++) {
            DataModel result = results.get(i);
            if (result.vin.equals(vin)) {
                //存储数据
                DataSubModel dataSubModel = new DataSubModel();
                dataSubModel.time = time;
                dataSubModel.lng = lng;
                dataSubModel.lat = lat;
                dataSubModel.speed = speed;
                result.datas.add(dataSubModel);
                Collections.sort(result.datas, new DataSubModelComparator());
                //检查发动机转速
                result.isSpeed = speed == config.speed;
                //检查定位
                result.isLocate = lng != 0 && lat != 0;
                //检查时间
                if (result.datas.size() <= 1) {
                    result.isOverTime = false;
                } else {
                    result.isOverTime = (time - result.datas.get(result.datas.size() - 1).time) / 1000 <= 1;
                }
                //总结论
                result.result = result.isSpeed && result.isLocate && result.isOverTime;
            }
        }


    }
}
