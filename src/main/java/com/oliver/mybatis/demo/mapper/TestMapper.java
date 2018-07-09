package com.oliver.mybatis.demo.mapper;

import com.oliver.mybatis.demo.model.Country;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by ouyjm on 2018/7/5.
 */
public interface TestMapper {
    @Results(id="countryResultMap",value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "countryname", column = "countryname"),
            @Result(property = "countrycode", column = "countrycode")
    })
    @Select({"select id, countryname, countrycode from country"})
    List<Country> selectAll();

    @ResultMap("countryResultMap")
    @Select({"select id, countryname, countrycode from country where countryname = #{0}"})
    List<Country> selectByName(String name);

    @Insert({"insert into country(countryname, countrycode) values(#{countryname}, #{countrycode})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert (Country country);

    @Update({"update country set countryname = #{countryname}, countrycode = #{countrycode} where id = #{id}"})
    int update(Country country);

    @Delete({"delete from country where id = #{id}"})
    int delete(Long id);
}
