package com.oliver.mybatis.demo.web;

import com.oliver.mybatis.demo.mapper.CountryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ouyjm on 2018/7/5.
 */
@RestController
public class CountryController {
    @Autowired
    private CountryMapper countryMapper;

    @GetMapping("/country")
    public Object getCountry(){
        return countryMapper.selectAll();
    }


    @GetMapping("/selectByName")
    public Object selectByName(String name){
        return countryMapper.selectByName(name);
    }

    @GetMapping("/selectByNameOrCode")
    public Object selectByNameOrCode(String name,String code){
        return countryMapper.selectByNameOrCode(name,code);
    }

    @GetMapping("/selectByIds")
    public Object selectByIds(String ids){
        List<Integer> list = new ArrayList<Integer>();
        String[] array = ids.split(",");
        for(String str:array){
            list.add(Integer.parseInt(str));
        }
        return countryMapper.selectByIds(list);
    }
}
