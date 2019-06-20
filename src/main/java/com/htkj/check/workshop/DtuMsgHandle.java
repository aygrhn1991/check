/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htkj.check.workshop;

import com.htkj.check.ConfigModel;
import com.htkj.check.DataHandler;
import com.htkj.check.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Administrator
 */
@Component("dtuMsgHandle")
public class DtuMsgHandle {

    @Autowired
    ConfigModel configModel;

    public static boolean on = false;
    public static List<DataModel> results = null;

    public void go(MetaMsg mm) {

        if (on && mm.order == MetaMsg.DOWN_LNGLAT) {
            DataHandler.h(mm.getData(), configModel, results);
        }

    }
}
