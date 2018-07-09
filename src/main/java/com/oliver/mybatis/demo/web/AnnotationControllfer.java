package com.oliver.mybatis.demo.web;

import com.oliver.mybatis.demo.mapper.TestMapper;
import com.oliver.mybatis.demo.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ouyjm on 2018/7/6.
 */
@RestController
public class AnnotationControllfer {
    @Autowired
    private TestMapper testMapper;

    @GetMapping("a/selectAll")
    public Object testSelectAll(){
        List<Country> list =  testMapper.selectAll();
        return list;
    }

    @GetMapping("a/selectByName")
    public Object testSelectByName(){
        List<Country> list =  testMapper.selectByName("中国");
        return list;
    }


    @GetMapping("a/insert")
    public Object testInsert(){
        Country country = new Country();
        country.setCountryname("韩国");
        country.setCountrycode("KR");
        int result =  testMapper.insert(country);
        return country.getId();
    }

    @GetMapping("a/update")
    public Object testUpdate(Long id){
        Country country = new Country();
        country.setCountryname("韩国");
        country.setCountrycode("KR1");
        country.setId(id);
        int result =  testMapper.update(country);
        return result;
    }

    @GetMapping("a/delete")
    public Object testDelete(Long id){
        int result =  testMapper.delete(id);
        return result;
    }
}
