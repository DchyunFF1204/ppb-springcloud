package com.ppb.controller;

import com.ppb.util.code.CodeFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成入口
 *
 * @author daizy
 * @create 2017-05-16 13:32
 */
@Controller
public class CodeController {

    public static final Map<String,String> methodMap = new HashMap<String,String>();

    static {
        methodMap.put("","");

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
     * 生成代码
     * @param tableName   表名称
     * @param keyType   key类型
     */
    @RequestMapping("/beginBuild")
    @ResponseBody
    public void beginBuild(String tableName,String keyType){
        CodeFactory.codeGenerate(tableName, "", "", keyType, "pages",true);
    }

}
