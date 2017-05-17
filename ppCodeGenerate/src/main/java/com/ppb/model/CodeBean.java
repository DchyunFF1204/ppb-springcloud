package com.ppb.model;

import java.io.Serializable;

/**
 * 代码生成入参
 *
 * @author daizy
 * @create 2017-05-17 15:42
 */
@SuppressWarnings("serial")
public class CodeBean implements Serializable{

    private String datasourceDriver;
    private String datasourceUrl;
    private String datasourceUserName;
    private String datasourceUserPwd;
    private String codeTargetProject;
    private String codeModelPackage;
    private String codeMapperPackage;

    private String codeServicePackage;

    private String codeControllerPackage;

    private String codeFtlPackage;

    private String tableNames;

    private String codeMethod;

    public String getDatasourceDriver() {
        return datasourceDriver;
    }

    public void setDatasourceDriver(String datasourceDriver) {
        this.datasourceDriver = datasourceDriver;
    }

    public String getDatasourceUrl() {
        return datasourceUrl;
    }

    public void setDatasourceUrl(String datasourceUrl) {
        this.datasourceUrl = datasourceUrl;
    }

    public String getDatasourceUserName() {
        return datasourceUserName;
    }

    public void setDatasourceUserName(String datasourceUserName) {
        this.datasourceUserName = datasourceUserName;
    }

    public String getDatasourceUserPwd() {
        return datasourceUserPwd;
    }

    public void setDatasourceUserPwd(String datasourceUserPwd) {
        this.datasourceUserPwd = datasourceUserPwd;
    }

    public String getCodeTargetProject() {
        return codeTargetProject;
    }

    public void setCodeTargetProject(String codeTargetProject) {
        this.codeTargetProject = codeTargetProject;
    }

    public String getCodeModelPackage() {
        return codeModelPackage;
    }

    public void setCodeModelPackage(String codeModelPackage) {
        this.codeModelPackage = codeModelPackage;
    }

    public String getCodeMapperPackage() {
        return codeMapperPackage;
    }

    public void setCodeMapperPackage(String codeMapperPackage) {
        this.codeMapperPackage = codeMapperPackage;
    }

    public String getCodeServicePackage() {
        return codeServicePackage;
    }

    public void setCodeServicePackage(String codeServicePackage) {
        this.codeServicePackage = codeServicePackage;
    }

    public String getCodeControllerPackage() {
        return codeControllerPackage;
    }

    public void setCodeControllerPackage(String codeControllerPackage) {
        this.codeControllerPackage = codeControllerPackage;
    }

    public String getCodeFtlPackage() {
        return codeFtlPackage;
    }

    public void setCodeFtlPackage(String codeFtlPackage) {
        this.codeFtlPackage = codeFtlPackage;
    }

    public String getTableNames() {
        return tableNames;
    }

    public void setTableNames(String tableNames) {
        this.tableNames = tableNames;
    }

    public String getCodeMethod() {
        return codeMethod;
    }

    public void setCodeMethod(String codeMethod) {
        this.codeMethod = codeMethod;
    }
}
