/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htkj.check.g6.workshop;

import com.htkj.check.g6.ConfigModel;
import com.htkj.check.g6.DataHandler;
import com.htkj.check.g6.DataModel;
import org.ht.iov.service.core.g6.LoginIn;
import org.ht.iov.service.core.std.VehIO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Component("dtuMsgHandle")
public class DtuMsgHandle {

    public static boolean on = false;
    public static ConfigModel config = null;
    public static List<DataModel> vins = null;

    public void go(MetaMsg mm) {
        System.out.println(new Date().getTime());
        if (on && (mm.order == MetaMsg.DOWN_LOGIN)) {
            LoginIn loginIn = (LoginIn) mm.getData();
            String vin = loginIn.getVin();
            String iccid = loginIn.getIccid();
            for (DataModel v : vins) {
                if (v.vin.equals(vin)) {
                    if (v.iccid == null || !v.iccid.equals(iccid)) {
                        v.iccid = iccid;
                        v.isInTime = true;
                        v.isLocate = false;
                        v.isSpeed = false;
                        v.isTorque = false;
                        v.isLevel = false;
                        v.result = false;
                        v.datas = new ArrayList<>();
                    }
                }
            }
        }
        if (on && (mm.order == MetaMsg.DOWN_LNGLAT)) {
            DataHandler.h(mm.getData(), config, vins);
        }


    }
}
