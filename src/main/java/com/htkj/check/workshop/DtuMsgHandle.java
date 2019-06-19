/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htkj.check.workshop;

import org.springframework.stereotype.Component;

/**
 *
 * @author Administrator
 */
@Component("dtuMsgHandle")
public class DtuMsgHandle {

    public void go(MetaMsg mm) {
        System.out.println(mm);
    }
}
