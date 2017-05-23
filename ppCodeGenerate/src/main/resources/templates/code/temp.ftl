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
                        <div class="col-sm-5" style="padding-top: 10px;">
                            <div class="input-group date form_datetime" data-date-format="yyyy-mm-dd hh:ii:ss">
                                <input type="text" size="28" readonly class="form-control" id="form_datetime_start" >
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
                            <button id="btn_query" type="button" class="btn btn btn-primary js-table-query">
                                <span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询
                            </button>
                            <button id="btn_add" type="button" class="btn btn btn-primary  js-table-add">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <table id="tb_departments" class="table table-striped table-hover"></table>
    </div>
</div>

<div class="modal fade draggable " id="js_table_modal"  data-backdrop="static" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 50%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="hTitle">新增or编辑操作</h4>
            </div>
            <div class="modal-body" tabindex="2" style="outline: none;">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn default glyphicon glyphicon-share-alt" data-dismiss="modal"><i class="icon-remove"></i>关闭</button>
                <button type="button" class="btn btn-primary glyphicon glyphicon-ok-circle" ><i class="icon-remove"></i>保存</button>
            </div>
        </div>
    </div>
</div>
<script src="static/assets/global/plugins/jquery.min.js"></script>
<script src="static/assets/global/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="static/assets/global/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="static/assets/global/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="static/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js"></script>
<script src="static/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="static/js/main/tool.js"></script>
<script src="static/temp/temp.js"></script>
</body>
</html>