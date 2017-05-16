<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>代码生成</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="static/css/bootstrap.min.css">
    <script src="static/js/jquery-2.0.3.min.js"></script>
    <script src="static/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    代码生成工具
                </h1>
            </div>
            <div class="col-lg-9 col-lg-offset-1">
                <legend>数据源配置
                    <small class="small">dataSource configure</small>
                </legend>
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="datasourceDriver" class="col-sm-2 control-label">数据源驱动</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="datasourceDriver" type="text"
                                   placeholder="com.mysql.jdbc.Driver"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="datasourceUrl" class="col-sm-2 control-label">数据源地址</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="datasourceUrl" type="text"
                                   placeholder="jdbc:mysql://127.0.0.1:3306/jeecgmybatis?useUnicode=true&characterEncoding=UTF-8"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="datasourceUserName" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="datasourceUserName" type="text" placeholder="root"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="datasourceUserPwd" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="datasourceUserPwd" type="password" placeholder="数据库密码"/>
                        </div>
                    </div>
                </form>

                <br/>

                <legend>代码路径配置
                    <small>code path configure</small>
                </legend>
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="codeTargetProject" class="col-sm-2 control-label">target.project</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="codeTargetProject" type="text"
                                   placeholder="E:/wordspace/ppb-autoCode/src/main/"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="codeModelPackage" class="col-sm-2 control-label">model.package</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="codeModelPackage" type="text"
                                   placeholder="com.ppb.autoCode.model"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="codeMapperPackage" class="col-sm-2 control-label">mapper.package</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="codeMapperPackage" type="text"
                                   placeholder="com.ppb.autoCode.dao"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="codeServicePackage" class="col-sm-2 control-label">service.package</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="codeServicePackage" type="text"
                                   placeholder="com.ppb.autoCode.service"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="codeControllerPackage" class="col-sm-2 control-label">controller.package</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="codeControllerPackage" type="text"
                                   placeholder="com.ppb.autoCode.controller"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="codeFtlPackage" class="col-sm-2 control-label">ftl.package</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="codeFtlPackage" type="text"
                                   placeholder="E:/wordspace/ppb-autoCode/src/main/webapp/WEB-INF/view"/>
                        </div>
                    </div>
                </form>
                <div class="pull-right">
                    <button type="button" class="btn btn-primary glyphicon glyphicon-circle-arrow-left"
                            onclick="restFrom()">重置
                    </button>
                    <button type="button" class="btn btn-primary glyphicon glyphicon-ok-circle">代码生成</button>
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    function restFrom() {
        $("body form")[0].reset();
    }

</script>
</body>
</html>