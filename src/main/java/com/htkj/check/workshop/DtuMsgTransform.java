package com.htkj.check.workshop;

import com.htkj.check.zmq.FlowCounter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ht.iov.service.core.g6.DataPack_G6;
import org.ht.iov.service.core.g6.EngineData;
import org.ht.iov.service.core.g6.G6TransfromFactory;
import org.ht.iov.service.core.g6.Obd;
import org.ht.iov.service.core.std.Lnglat;
import org.ht.iov.service.core.std.VehIO;
import org.ht.iov.service.core.tbox.TransformFactory;
import org.ht.utils.core.Tuple3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 终端消息转换服务，zmq接收的json消息转换为 各类 metamsg
 *
 * @author jineral
 * @date 2019-02-28
 */
@Component("dtuMsgTransform")
public class DtuMsgTransform {

    TransformFactory fac = new TransformFactory();
    @Autowired
    G6TransfromFactory fac_g6;
    @Autowired
    FlowCounter counter;

    /**
     * json转换为 各类 metamsg
     *
     * @param json
     * @return
     */
    public List<MetaMsg> toMetaMsg(String json) {
        counter.toMetaMsg.incrementAndGet();
        try {
            //g6终端 数据转换处理
            return this.g6toMetaMsg(json);

        } catch (IOException ex) {
            Logger.getLogger(DtuMsgTransform.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<MetaMsg> g6toMetaMsg(String json) throws IOException {
        DataPack_G6 dp = fac_g6.json2DataPack(json);
        if (dp.getVehid() <= 0) {
            return null;
        }
        List<MetaMsg> lst = new ArrayList<>();
        switch (dp.getCommandId()) {
            case G6TransfromFactory.CMD_LOGIN:
                //登入消息
                VehIO vehIn = fac_g6.toVehIn(dp);
                lst.add(new MetaMsg<>(MetaMsg.DOWN_LOGIN, vehIn));
                break;
            case G6TransfromFactory.CMD_LOGOT:
                //登出消息
                VehIO vehOut = fac_g6.toVehOut(dp);
                lst.add(new MetaMsg<>(MetaMsg.DOWN_LOGOUT, vehOut));
                break;
            case G6TransfromFactory.CMD_RT_UP:
                //实时坐标消息
                Tuple3<EngineData, Obd, Lnglat> rt = fac_g6.toRealTime(dp);
                lst.add(new MetaMsg<>(MetaMsg.DOWN_LNGLAT, rt));
//                if (rt.getC() != null) {
//                    lst.add(new MetaMsg<>(MetaMsg.DOWN_LNGLAT, rt.getC()));
//                }
//
//                //发动机消息
//                if (rt.getA() != null) {
//                    lst.add(new MetaMsg<>(MetaMsg.DOWN_G6_ENG, rt.getA()));
//                }
//                //OBD消息
//                if (rt.getB() != null) {
//                    lst.add(new MetaMsg<>(MetaMsg.DOWN_G6_OBD, rt.getB()));
//                }
                break;
        }
        return lst;
    }
}
