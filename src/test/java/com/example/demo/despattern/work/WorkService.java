package com.example.demo.despattern.work;

import com.example.demo.despattern.city.City;
import com.example.demo.despattern.service.AbstractService;
import com.example.demo.despattern.service.AnHuiTest;

/**
 * @author zph  on 2018/3/12
 */
public class WorkService extends BaseServiceDb {

    public AbstractService abstractService;

    public WorkService(String pCode){
        init(pCode);
    }

    public void  run(){


    }




    public void init(String pCode) {
        switch (pCode) {
            case City.AH:
                abstractService = new AnHuiTest();
                break;
            case City.SH:
                abstractService = new AnHuiTest();
                break;
            case City.SC:
                abstractService = new AnHuiTest();
                break;
        }
    }


}
