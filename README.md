# mybatis-demo


# 注意事项：
设置typeAliasesPackage，该包下的类在mapper.xml中可以使用类名，不需要带包名
mybatis.typeAliasesPackage=com.oliver.mybatis.demo.model
<typeAliases>
    <package name="com.oliver.mybatis.demo.model"/>
</typeAliases>



代码使用下划线转驼峰方式，注意数据库命名和实体类生成

配置<setting name="mapUnderscoreToCamelCase" value="true"/>可以将全局mapper.xml中的下划线映射到实体类对应的驼峰属性中，如user_name 会映射到userName上，无需定义ResultMap

mapper接口中有多个参数，需要使用“@Parma”注解设置参数名字，区分参数，默认参数名为0，1  param1，param2
List<Country> selectByName(@Param("name") String name);
<select id="selectByName"  resultType="Country">
        <bind name="nameLike" value="'%'+name+'%'" />
        select id,countryname,countrycode from country where countryname like #{nameLike}
    </select>


使用Map传递参数，如果不指定名字，使用默认值_parameter

添加获取自增长主键
1.使用JDBC
<insert id="insert1" keyProperty="id" useGeneratedKeys="true" parameterType="com.oliver.mybatis.demo.model.Country">
    insert into country(countryname,countrycode) values(#{countryname},#{countrycode})
</insert>
2.使用selectKey
<insert id="insert2" keyProperty="id">
    insert into country(countryname,countrycode) values(#{countryname},#{countrycode})
    <selectKey keyColumn="id" keyProperty="id" resultType="long" order="AFTER">
        select LAST_INSERT_ID()
    </selectKey>
</insert>


使用mybatis尽量不用基本类型，有默认值，无法进行“!=null”判断，可以使用封装类

字符串为空判断使用“str!=null and str!=''”

集合为空判断使用“list!=null and list.size()>0”
<select id="selectByIds"  resultType="Country">
    select id,countryname,countrycode from country
    <where>
        <choose>
            <when test="ids!=null and ids.size()>0">
                id in
                <foreach collection="ids" item="id" open="(" close=")" separator=",">
                    #{id}
                </foreach>
            </when>
            <otherwise>
                1=2
            </otherwise>
        </choose>
    </where>
    </select>


可以使用choose when when otherwise实现if elseif else功能
<select id="selectByNameOrCode"  resultType="Country">
        select id,countryname,countrycode from country
        <where>
            <choose>
                <when test="name!=null and name!=''">
                    and countryname=#{name,jdbcType=VARCHAR}
                </when>
                <when test="code!=null and code!=''">
                    and countrycode=#{code,jdbcType=VARCHAR}
                </when>
                <otherwise>
                    and 1=2
                </otherwise>
            </choose>
        </where>
    </select>



like字段，mysql可以使用concat函数，非mysql数据库可以使用bind标签获取值
<select id="selectByName"  resultType="Country">
        <bind name="nameLike" value="'%'+name+'%'" />
        select id,countryname,countrycode from country where countryname like #{nameLike}
    </select>


调用存储过程
<select id="testCallable1" statementType="CALLABLE" useCache="false">
        {
        call pro_1(
            #{id,mode=IN,jdbcType=BIGINT}
        )
        }
    </select>
    <insert id="testCallable2" statementType="CALLABLE"></insert>
    <delete id="testCallable3" statementType="CALLABLE"></delete>


注解方式使用
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








