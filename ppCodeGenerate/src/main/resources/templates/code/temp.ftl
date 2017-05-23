<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>代码生成</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link href="static/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="static/assets/global/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="static/assets/global/css/components.min.css">
    <link rel="stylesheet" href="static/assets/global/css/plugins.min.css">
    <link rel="stylesheet" href="static/assets/global/plugins/bootstrap-table/bootstrap-table.min.css">
    <link rel="stylesheet" href="static/assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css">

</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="page-header">
                <h1>
                    页标题范例 <small>此处编写页标题</small>
                </h1>
            </div>
        </div>
    </div>
    <div class="panel-body" style="padding-bottom:0px;">
        <div class="panel panel-primary">
            <div class="panel-heading">查询条件</div>
            <div class="panel-body">
                <form id="formSearch" class="form-horizontal">
                    <div class="form-group" style="margin-top:15px">
                        <label class="control-label col-sm-1" for="txt_search_statu">文本域</label>
                        <div class="col-sm-2">
                            <textarea class="form-control" rows="3"></textarea>
                        </div>

                        <label class="control-label col-sm-1" for="txt_search_departmentname">文本参数</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="txt_search_departmentname">
                        </div>

                        <label class="control-label col-sm-1" for="txt_search_statu">单选</label>
                        <div class="col-sm-2" >
                            <select class="form-control" id="txt_search_statu" name="txt_search_statu">
                                <option value="">All</option>
                                <option value="1">选项一</option>
                                <option value="1">选项二</option>
                            </select>
                        </div>

                        <label class="control-label col-sm-1" for="txt_search_statu">多选</label>
                        <div class="col-sm-2">
                            <select id="usertype" name="usertype" class="selectpicker show-tick form-control" multiple data-live-search="false">
                                <option value="0">苹果</option>
                                <option value="1">菠萝</option>
                                <option value="2">香蕉</option>
                            </select>
                        </div>

                        <label class="control-label col-sm-1" for="txt_search_statu" style="padding-top: 17px;">时间</label>
                        <div class="col-sm-4" style="padding-top: 10px;">
                            <div class="input-group date form_datetime" data-date-format="yyyy-mm-dd hh:ii:ss">
                                <input type="text" size="24" readonly class="form-control" id="form_datetime_start" >
                                <span class="input-group-btn">
                                    <button class="btn default date-set" type="button">
                                        <i class="fa fa-calendar"></i>  至
                                    </button>
                                </span>
                                <input type="text" size="24" readonly class="form-control" id="form_datetime_end" >
                                <span class="input-group-btn">
                                    <button class="btn default date-reset" type="button">
                                        <i class="fa fa-calendar"></i>
                                    </button>
                                </span>
                            </div>
                        </div>

                        <div class="col-sm-7 col-md-offset-5" style="margin-top:15px">
                            <button id="btn_query" type="button" class="btn btn btn-primary">
                                <span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询
                            </button>
                            <button id="btn_add" type="button" class="btn btn btn-primary">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div id="toolbar" >
            <button id="btn_edit" type="button" class="btn btn btn-primary">
                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
            </button>

            <button id="btn_delete" type="button" class="btn btn btn-primary">
                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>删除
            </button>
        </div>
        <table id="tb_departments" class="table table-striped table-hover"></table>

    </div>
</div>
<script src="static/assets/global/plugins/jquery.min.js"></script>
<script src="static/assets/global/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="static/assets/global/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="static/assets/global/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="static/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js"></script>
<script src="static/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="static/js/main/tool.js"></script>
<script>

    $('#usertype').selectpicker({
        'selectedText': 'cat'
    });

    $("#form_datetime_start").datetimepicker({
        format: "yyyy-mm-dd hh:ii:ss",
        autoclose: true,
        todayBtn: true,
        startDate: "2013-02-14 10:00:01",
        pickerPosition: "bottom-left",
        minuteStep: 10
    });
    $("#form_datetime_end").datetimepicker({
        format: "yyyy-mm-dd hh:ii:ss",
        autoclose: true,
        todayBtn: true,
        startDate: "2013-02-14 10:00:01",
        pickerPosition: "bottom-left",
        minuteStep: 10
    });

    $(function () {
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();
        //2.初始化Button的点击事件
        var oButtonInit = new ButtonInit();
        oButtonInit.Init();

    });


    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#tb_departments').bootstrapTable({
                url: '/Home/GetDepartment', //请求后台的URL（*）
                method: 'get', //请求方式（*）
                //toolbar: '#toolbar', //工具按钮用哪个容器
                striped: true, //是否显示行间隔色
                cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true, //是否显示分页（*）
                sortable: false, //是否启用排序
                sortOrder: "asc", //排序方式
                queryParams: oTableInit.queryParams,//传递参数（*）
                sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1, //初始化加载第一页，默认第一页
                pageSize: 10, //每页的记录行数（*）
                pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                strictSearch: false,
                showColumns: true, //是否显示所有的列
                showRefresh: true, //是否显示刷新按钮
                minimumCountColumns: 2, //最少允许的列数
                clickToSelect: true, //是否启用点击选中行
                height: "auto", //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "ID", //每一行的唯一标识，一般为主键列
                showToggle:true, //是否显示详细视图和列表视图的切换按钮
                cardView: false, //是否显示详细视图
                detailView: false, //是否显示父子表
                columns: [{
                    checkbox: true
                }, {
                    field: 'Name',
                    title: '参数'
                }, {
                    field: 'ParentName',
                    title: '参数'
                }, {
                    field: 'Level',
                    title: '参数'
                }, {
                    field: 'Desc',
                    title: '参数'
                }, {
                    field: 'use',
                    title: '操作'
                }]
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit, //页面大小
                offset: params.offset, //页码
                departmentname: $("#txt_search_departmentname").val(),
                statu: $("#txt_search_statu").val()
            };
            return temp;
        };
        return oTableInit;
    };


    var ButtonInit = function () {
        var oInit = new Object();
        var postdata = {};

        oInit.Init = function () {
            //初始化页面上面的按钮事件
        };
        return oInit;
    };
</script>
</body>
</html>