package com.oliver.mybatis.demo.mapper;

import com.oliver.mybatis.demo.model.Country;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ouyjm on 2018/7/2.
 */
public interface CountryMapper {
    List<Country> selectAll();

    List<Country> selectByName(@Param("name") String name);

    List<Country> selectByNameOrCode(@Param("name") String name,@Param("code") String code);

    List<Country> selectByIds(@Param("ids")List<Integer> ids);
}
