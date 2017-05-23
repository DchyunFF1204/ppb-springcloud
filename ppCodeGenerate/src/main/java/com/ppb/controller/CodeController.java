package com.ppb.controller;

import com.ppb.model.CodeBean;
import com.ppb.util.code.CodeFactory;
import com.ppb.util.code.MysqlFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * 代码生成入口
 *
 * @author daizy
 * @create 2017-05-16 13:32
 */
@Controller
public class CodeController {


    @RequestMapping("/toTest")
    public String toTest(){
        return "code/temp";
    }


    /**
     * 进入代码生成配置页面
     * @return
     */
    @RequestMapping("/toCodePage")
    public String toCodePage(){
        return "code/codePage";
    }

    /**
     * 获取数据库表信息
     * @param datasourceDriver
     * @param datasourceUrl
     * @param datasourceUserName
     * @param datasourceUserPwd
     * @return
     */
    @RequestMapping("/getTables")
    @ResponseBody
    public List<String> getTables(String datasourceDriver,String datasourceUrl, String datasourceUserName, String datasourceUserPwd){
        Connection con  = MysqlFactory.getInstance(datasourceDriver).getConnection(datasourceUrl,datasourceUserName,datasourceUserPwd);
        return MysqlFactory.getInstance(datasourceDriver).getTables(con);
    }

    /**
     * 生成代码
     */
    @RequestMapping("/beginBuild")
    @ResponseBody
    public void beginBuild(CodeBean codeBean){
        CodeFactory.buildCode(codeBean);
    }

}
