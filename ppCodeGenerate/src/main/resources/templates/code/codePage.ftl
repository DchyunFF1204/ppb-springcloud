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
    <script src="static/js/main/tool.js"></script>
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
                <form class="form-horizontal" role="form" id="form_datasource">
                    <div class="form-group">
                        <label for="datasourceDriver" class="col-sm-2 control-label">数据源驱动</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="datasourceDriver" name="datasourceDriver" type="text"
                                   value="com.mysql.jdbc.Driver" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="datasourceUrl" class="col-sm-2 control-label">数据源地址</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="datasourceUrl" name="datasourceUrl" type="text"
                                   value="jdbc:mysql://127.0.0.1:3306/jeecgmybatis?useUnicode=true&characterEncoding=UTF-8"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="datasourceUserName" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="datasourceUserName" name="datasourceUserName" type="text"
                                   value="root"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="datasourceUserPwd" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="datasourceUserPwd" name="datasourceUserPwd" type="password"
                                   value="root"/>
                        </div>
                    </div>
                </form>
                <div class="pull-right">
                    <button type="button" class="btn btn-primary glyphicon glyphicon-circle-arrow-left"
                            onclick="restFrom('form_datasource')">重置
                    </button>
                    <button type="button" class="btn btn-primary glyphicon glyphicon-ok-circle"
                            onclick="findTables()">获取数据表信息
                    </button>
                </div>

                <br/>
                <br/>
                <div class="row clearfix">
                    <div class="col-md-6 column">
                        <div class="list-group">
                            <a href="#" class="list-group-item active">数据表列表</a>
                            <div class="list-group-item">
                                选择数据库表，再生成对应的java代码
                            </div>
                            <div class="list-group-item pre-scrollable" style="height:300px" id="datatable_list">

                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 column">
                        <div class="list-group">
                            <a href="#" class="list-group-item active">操作选择</a>
                            <div class="list-group-item">
                                选择操作方法，生成对应的java代码
                            </div>
                            <div class="list-group-item pre-scrollable" style="height:300px" id="codeMethod">
                                <li class="list-group-item">
                                    <div class="checkbox">
                                        <label><input type="checkbox"  data-type="1"/>查询</label>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="checkbox">
                                        <label><input type="checkbox"  data-type="2"/>分页</label>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="checkbox">
                                        <label><input type="checkbox"  data-type="3"/>新增</label>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="checkbox">
                                        <label><input type="checkbox"  data-type="4"/>编辑</label>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="checkbox">
                                        <label><input type="checkbox" data-type="5"/>删除</label>
                                    </div>
                                </li>
                            </div>
                        </div>
                    </div>
                </div>

                <br/>

                <legend>代码路径配置
                    <small>code path configure</small>
                </legend>
                <form class="form-horizontal" role="form" id="form_codePath">
                    <div class="form-group">
                        <label for="codeTargetProject" class="col-sm-2 control-label">target.project</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="codeTargetProject" type="text" name="codeTargetProject"
                                   value="E:/wordspace/ppb-autoCode/src/main/"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="codeModelPackage" class="col-sm-2 control-label">model.package</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="codeModelPackage" type="text" name="codeModelPackage"
                                   value="com.ppb.autoCode.model"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="codeMapperPackage" class="col-sm-2 control-label">mapper.package</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="codeMapperPackage" type="text" name="codeMapperPackage"
                                   value="com.ppb.autoCode.dao"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="codeServicePackage" class="col-sm-2 control-label">service.package</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="codeServicePackage" type="text" name="codeServicePackage"
                                   value="com.ppb.autoCode.service"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="codeControllerPackage" class="col-sm-2 control-label">controller.package</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="codeControllerPackage"  name="codeControllerPackage" type="text"
                                   value="com.ppb.autoCode.controller"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="codeFtlPackage" class="col-sm-2 control-label">ftl.package</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="codeFtlPackage" type="text" name="codeFtlPackage"
                                   value="E:/wordspace/ppb-autoCode/src/main/webapp/WEB-INF/view"/>
                        </div>
                    </div>
                </form>
                <div class="pull-right">
                    <button type="button" class="btn btn-primary glyphicon glyphicon-circle-arrow-left"
                            onclick="restFrom('form_codePath')">重置
                    </button>
                    <button type="button" class="btn btn-primary glyphicon glyphicon-ok-circle" onclick="buildCode()">代码生成</button>
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    function restFrom(formId) {
        $("#" + formId)[0].reset();
    }

    function findTables() {
        var queryString = $('#form_datasource').serialize();
        $.post('/getTables', queryString, function (data) {
            // 循环遍历
            var trs = '';
            $.each(data,function(n,th){
                trs+='<li class="list-group-item">';
                trs+='<div class="checkbox">';
                trs+='<label><input type="checkbox" data-tablename="'+th+'"/>'+th+'</label>';
                trs+='</div>';
                trs+='</li>';
            });
            $('#datatable_list').html(trs);
        });
    }

    function buildCode(){
        var codeMethod = '';
        $('#codeMethod').find('input[type="checkbox"]:checked').map(function(){
            codeMethod += $(this).data('type')+',';
        })
        codeMethod = codeMethod.substring(0, codeMethod.length-1);

        var tableNames = '';
        $('#datatable_list').find('input[type="checkbox"]:checked').map(function(){
            tableNames += $(this).data('tablename')+',';
        })
        tableNames = tableNames.substring(0, tableNames.length-1);

        var form_datasource = $('#form_datasource').serializeJson();
        var form_codePath = $('#form_codePath').serializeJson();
        var param = $.extend({}, form_datasource,form_codePath);
        param.tableNames = tableNames;
        param.codeMethod = codeMethod;
        console.log(param);
        $.post('/beginBuild', param, function (data) {

        },'json')
    }
</script>
</body>
</html>