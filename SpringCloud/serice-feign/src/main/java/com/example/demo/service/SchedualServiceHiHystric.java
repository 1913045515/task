package com.example.demo.service;

import org.springframework.stereotype.Component;

/**
 * Created by lzq on 2018/3/1.
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }
}