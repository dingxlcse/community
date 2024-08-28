package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
//@Scope("prototype")//参数控制，默认single单例
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;

    public AlphaService() {
        System.out.println("实例化AlphaService");
    }

    @PostConstruct//在构造器之后调用
    public void init(){
        System.out.println("初始化AlphaService");
    }
    @PreDestroy//在销毁之前调用
    public void destroy(){
        System.out.println("销毁AlphaService");
    }
    public String find(){
        return alphaDao.select();
    }
}
