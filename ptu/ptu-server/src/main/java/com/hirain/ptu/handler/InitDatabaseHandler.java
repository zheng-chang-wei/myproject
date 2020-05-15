package com.hirain.ptu.handler;

import com.hirain.ptu.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author changwei.zheng
 * @date 2020/5/11 11:57
 * @describe
 */
@Component
public class InitDatabaseHandler {
    @Autowired
    ManageService manageService;
    @PostConstruct
    public void initDatabase(){
        manageService.createDatabase("ptu_data");
    }
}
