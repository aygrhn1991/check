/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htkj.check.g6.workshop;

import com.htkj.check.g6.ConfigModel;
import com.htkj.check.g6.DataHandler;
import com.htkj.check.g6.DataModel;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Component("dtuMsgHandle")
public class DtuMsgHandle {

    public static boolean on = false;
    public static ConfigModel config = null;
    public static List<DataModel> results = null;

    public void go(MetaMsg mm) {
        System.out.println(new Date().getTime());
        if (on && mm.order == MetaMsg.DOWN_LNGLAT) {
            DataHandler.h(mm.getData(), config, results);
        }

    }
}
