$(function () {
    // 初始化条件框
    tempJs.initParamForm();
    //初始化Table
    tempJs.initTable();
    // 绑定静态button事件
    tempJs.initButton();
});

var tempJs = {
    queryTableLink : "/queryPg",   // 查询列表server url
    queryDataByPriId : "/queryById",// 主键查询 server url
    saveData : "/save",   // 数据保存 server url
    // 初始化条件栏位
    initParamForm : function(){
        $('#usertype').selectpicker({
            'selectedText': 'cat'
        });

        $("#form_datetime_start").datetimepicker({
            format: "yyyy-mm-dd hh:ii:ss",
            autoclose: true,
            todayBtn: true,
            pickerPosition: "bottom-left",
            minuteStep: 10
        });
        $("#form_datetime_end").datetimepicker({
            format: "yyyy-mm-dd hh:ii:ss",
            autoclose: true,
            todayBtn: true,
            pickerPosition: "bottom-left",
            minuteStep: 10
        });
    },
    // 初始化data table
    initTable : function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#tb_departments').bootstrapTable({
                url: tempJs.getQueryTableLink(), //请求后台的URL（*）
                method: 'post', //请求方式（*）
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
                    title: '操作',
                    formatter: function (value, rec) {
                        var ops = tempJs.setButtons([
                            {
                                type: 'edit',
                                title:'编辑',
                                param : rec.id,
                                selector : 'btn btn-sm blue',
                                icon : 'glyphicon glyphicon-pencil',
                                callFunction : 'tempJs.openAddOrEdieModal(this)'
                            },
                            {
                                type: 'del',
                                title:'删除',
                                param : rec.id,
                                selector : 'btn btn-sm blue',
                                icon : 'glyphicon glyphicon-minus',
                                callFunction : 'tempJs.removeData(this)'
                            }
                        ]);
                        return ops;
                    }
                }]
            });
        };
        //查询的参数
        oTableInit.queryParams = function (params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                'page': params.offset / params.limit + 1,
                'rows': params.limit
            };
            var param = $('#formSearch').serializeJson();
            temp = $.extend(temp, param);
            return temp;
        };
        return oTableInit;
    },
    // 动态button 设置及绑定事件
    setButtons : function (buttonsParam) {
        var ops = '';
        for (var bun in buttonsParam){
            ops +='<button type="button" class="'+bun.selector+'" data-param="'+bun.param+'" onclick="'+bun.callFunction+'"><span class="'+bun.icon+'" aria-hidden="true"></span>'+bun.title+'</button>';
        }
        return ops;
    },
    // 初始化页面静态button事件
    initButton : function () {
        // 查询
        $('.js-table-query').on('click', function () {
            tempJs.queryDataTable();
        });
        // 新增
        $('.js-table-add').on('click', function () {
            tempJs.openAddOrEdieModal();
        });
    },
    // 获取查询链接
    getQueryTableLink :function () {
        return tempJs.queryTableLink+"?rn="+Math.random();
    },
    // 查询table data
    queryDataTable:function () {
        $('#tb_departments').bootstrapTable('refresh' , {
            url: getQueryLink(),
            query: $('#formSearch').serialize()
        })
    },
    // 修改数据
    openAddOrEdieModal : function (_this) {
        $('#js_table_modal').modal('show');
    },
    // 删除数据
    removeData : function (_this) {
        Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
            if (!e) {
                return;
            }
            var useParam = $(_this).data('param');
            tempJs.saveData(useParam);
        });
    },
    // 保存数据
    saveData : function (useParam) {
        // ajax 请求
        $.post(tempJs.saveData, useParam, function (result) {
            if(result.success){
                tempJs.queryDataTable();
            }else{
                Ewin.alert(result.message);
            }
        });
    }
};