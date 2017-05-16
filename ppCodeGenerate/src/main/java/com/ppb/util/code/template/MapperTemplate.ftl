<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${daoPackage}.${className}Mapper" > 

	<!-- Result Map-->
	<resultMap id="BaseResultMap" type="${entityPackage}.${className}" >
	   <#list feilds as item>
		<result column="${item.columnName}" property="${item.mname}"/>
	   </#list>
	</resultMap>
	
	<sql id="Example_Where_Clause">
	    <where>
	      <foreach collection="oredCriteria" item="criteria" separator="or">
	        <if test="criteria.valid">
	          <trim prefix="(" prefixOverrides="and" suffix=")">
	            <foreach collection="criteria.criteria" item="criterion">
	              <choose>
	                <when test="criterion.noValue">
	                  and <#noparse>${criterion.condition}</#noparse>
	                </when>
	                <when test="criterion.singleValue">
	                  and <#noparse>${criterion.condition} #{criterion.value}</#noparse>
	                </when>
	                <when test="criterion.betweenValue">
	                  and <#noparse>${criterion.condition} #{criterion.value} and #{criterion.secondValue}</#noparse>
	                </when>
	                <when test="criterion.listValue">
	                  and <#noparse>${criterion.condition}</#noparse>
	                  <foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
	                    <#noparse>#{listItem}</#noparse>
	                  </foreach>
	                </when>
	              </choose>
	            </foreach>
	          </trim>
	        </if>
	      </foreach>
	    </where>
	  </sql>
	  <sql id="Update_By_Example_Where_Clause">
	    <where>
	      <foreach collection="example.oredCriteria" item="criteria" separator="or">
	        <if test="criteria.valid">
	          <trim prefix="(" prefixOverrides="and" suffix=")">
	            <foreach collection="criteria.criteria" item="criterion">
	              <choose>
	                <when test="criterion.noValue">
	                  and <#noparse>${criterion.condition}</#noparse>
	                </when>
	                <when test="criterion.singleValue">
	                  and <#noparse>${criterion.condition} #{criterion.value}</#noparse>
	                </when>
	                <when test="criterion.betweenValue">
	                  and <#noparse>${criterion.condition} #{criterion.value} and #{criterion.secondValue}</#noparse>
	                </when>
	                <when test="criterion.listValue">
	                  and <#noparse>${criterion.condition}</#noparse>
	                  <foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
	                    <#noparse>#{listItem}</#noparse>
	                  </foreach>
	                </when>
	              </choose>
	            </foreach>
	          </trim>
	        </if>
	      </foreach>
	    </where>
	</sql>
	


	<!-- $!{tableName} table all fields -->
	<sql id="Base_Column_List" >
		<#list feilds as fl>
        <#if fl_has_next>
          ${fl.columnName},
          <#else>
          ${fl.columnName}
        </#if>
      </#list>
	</sql>
	
	<select id="selectByExample" parameterType="${entityPackage}.${className}Example" resultMap="BaseResultMap">
	    select
	    <if test="distinct">
	      distinct
	    </if>
	    <include refid="Base_Column_List" />
	    from ${tableName}
	    <if test="_parameter != null">
	      <include refid="Example_Where_Clause" />
	    </if>
	    <if test="orderByClause != null">
	      order by <#noparse>${orderByClause}</#noparse>
	    </if>
	 </select>
	 
	<select id="selectByPrimaryKey" parameterType="java.lang.Long" resultMap="BaseResultMap">
	    select 
	    <include refid="Base_Column_List" />
	    from ${tableName}
	    where id = <#noparse>#{id}</#noparse>
	</select>
	
	<delete id="deleteByPrimaryKey" parameterType="java.lang.Long">
	    delete from ${tableName}
	    where id = <#noparse>#{id}</#noparse>
	</delete>
	
	
  <delete id="deleteByExample" parameterType="${entityPackage}.${className}Example">
	    delete from ${tableName}
	    <if test="_parameter != null">
	      <include refid="Example_Where_Clause" />
	    </if>
  </delete>
  
  <insert id="insert" parameterType="${entityPackage}.${className}">
    insert into ${tableName} (
      <#list feilds as fl>
        <#if fl_has_next>
          ${fl.columnName},
          <#else>
          ${fl.columnName}
        </#if>
      </#list>
      )
    values (
      <#list feilds as fl>
        <#if fl_has_next>
           <#noparse>#{</#noparse>${fl.mname}<#noparse>}</#noparse>,
          <#else>
          <#noparse>#{</#noparse>${fl.mname}<#noparse>}</#noparse>
        </#if>
      </#list>
      )
  </insert>
  
  
  <insert id="insertSelective" parameterType="${entityPackage}.${className}">
    insert into ${tableName}
    <trim prefix="(" suffix=")" suffixOverrides=",">
     <#list feilds as fl>
        <if test="${fl.mname} != null">
	        ${fl.columnName},
	    </if>
      </#list>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
       <#list feilds as fl>
        <if test="${fl.mname} != null">
	        <#noparse>#{</#noparse>${fl.mname}<#noparse>}</#noparse>,
	    </if>
      </#list>
    </trim>
  </insert>
  
  <select id="countByExample" parameterType="${entityPackage}.${className}Example" resultType="java.lang.Integer">
    select count(*) from ${tableName}
    <if test="_parameter != null">
      <include refid="Example_Where_Clause" />
    </if>
  </select>
  
  <update id="updateByExampleSelective" parameterType="map">
    update ${tableName}
    <set>
      <#list feilds as fl>
        <if test="record.${fl.mname} != null">
	        ${fl.columnName} = <#noparse>#{</#noparse>record.${fl.mname}<#noparse>}</#noparse>,
	    </if>
      </#list>
    </set>
    <if test="_parameter != null">
      <include refid="Update_By_Example_Where_Clause" />
    </if>
  </update>
  
   <update id="updateByExample" parameterType="map">
    update ${tableName}
    set 
    <#list feilds as fl>
    	<#if fl_has_next>
           ${fl.columnName} = <#noparse>#{</#noparse>${fl.mname}<#noparse>}</#noparse>,
          <#else>
         ${fl.columnName} = <#noparse>#{</#noparse>${fl.mname}<#noparse>}</#noparse>
        </#if>
    </#list>
    <if test="_parameter != null">
      <include refid="Update_By_Example_Where_Clause" />
    </if>
  </update>
  
  <update id="updateByPrimaryKeySelective" parameterType="${entityPackage}.${className}">
    update ${tableName}
    <set>
      <#list feilds as fl>
        <if test="${fl.mname} != null">
           ${fl.columnName} = <#noparse>#{</#noparse>${fl.mname}<#noparse>}</#noparse>,
        </if>
    </#list>
    </set>
    where id = <#noparse>#{id}</#noparse>
  </update>
  
  <update id="updateByPrimaryKey" parameterType="${entityPackage}.${className}">
    update groupon_user
    set <#list feilds as fl>
    	<#if fl_has_next>
           ${fl.columnName} = <#noparse>#{</#noparse>${fl.mname}<#noparse>}</#noparse>,
          <#else>
         ${fl.columnName} = <#noparse>#{</#noparse>${fl.mname}<#noparse>}</#noparse>
        </#if>
    </#list>
    where id = <#noparse>#{id}</#noparse>
  </update>

</mapper>   
