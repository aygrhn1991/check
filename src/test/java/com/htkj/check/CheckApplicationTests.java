package com.htkj.check;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckApplicationTests {

    @Test
    public void contextLoads() {

        boolean isPosition = (2 & 1) == 0;
        int status = 0 | (isPosition ? 0x02 : 0);
    int ss=3;
      int r=   (ss>>(1-1))&1;


        System.out.println(r);
    }

}
