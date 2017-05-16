<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title></title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <#noparse>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="${staticDomain}/statics/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="${staticDomain}/statics/assets/css/font-awesome.min.css" />
    <!--[if IE 7]>
    <link rel="stylesheet" href="${staticDomain}/statics/assets/css/font-awesome-ie7.min.css" />
    <![endif]-->
    <link rel="stylesheet" href="${staticDomain}/statics/assets/css/jquery-ui-1.10.3.full.min.css" />
    <link rel="stylesheet" href="${staticDomain}/statics/assets/css/datepicker.css" />
    <link rel="stylesheet" href="${staticDomain}/statics/assets/css/ui.jqgrid.css" />
    <link rel="stylesheet" href="${staticDomain}/statics/assets/css/ace.min.css" />
    <link rel="stylesheet" href="${staticDomain}/statics/assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="${staticDomain}/statics/assets/css/ace-skins.min.css" />
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="${staticDomain}/statics/assets/css/ace-ie.min.css" />
    <![endif]-->
    <link rel="stylesheet" href="${staticDomain}/statics/assets/css/ljhy.css" />
    <script src="${staticDomain}/statics/assets/js/ace-extra.min.js"></script>
    <!--[if lt IE 9]>
    <script src="${staticDomain}/statics/assets/js/html5shiv.js"></script>
    <script src="${staticDomain}/statics/assets/js/respond.min.js"></script>
    <![endif]-->
    </#noparse>
</head>
<body style="background:#fff">
	<div class="calls">
	    <div class="breadcrumbs" id="breadcrumbs">
	        <script type="text/javascript">
	            try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
	        </script>
	        <ul class="breadcrumb">
	            <li>
	                <i class="icon-home home-icon"></i>
	                <a href="#">配置管理</a>
	            </li>
	            <li class="active">数据源管理</li>
	        </ul>
	    </div>
	    <div class="page-content">
	        <div class="page-header">
	            <h1>
	              	  配置管理
	                <small>
	                    <i class="icon-double-angle-right"></i>数据源管理
	                </small>
	            </h1>
	        </div>
	        <div class="row">
	            <div class="col-xs-12">
	                <div class="row">
	                    <div class="col-sm-12">
	                        <form class="form-inline query_ipt">
	                            <div class="row">
	                                <div class="col-sm-10">
	                                    <div class="form-group ipt_item">
	                                        <span class="form_til">数据库类型</span>
	                                        <select class="form-control" id="dataType">
													<option value=""  selected>全部</option>
													<option value="1">mysql</option>
													<option value="2">oracle</option>
											</select>
	                                    </div>
	                                </div>
	                                <div class="col-sm-2">
	                                    <div  onclick="searchJqGrid()" style="vertical-align: bottom;" class="btn btn-default btn-sm">查询</div>
	                                </div>
	                            </div>
	                        </form>
	                    </div>
	                    <div class="col-xs-12">
	                        <h3 class="header smaller lighter blue"></h3>
	                        <table id="grid-table"></table>
	                        <div id="grid-pager"></div>
	                    </div>
	                    <div class="hr hr32 hr-dotted"></div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
<#noparse>
<script src="${staticDomain}/statics/assets/js/jquery-1.10.2.min.js"></script>
<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='${staticDomain}/statics/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
</script>
<!-- <![endif]-->
<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='${staticDomain}/statics/assets/js/jquery-1.10.2.min.js'>" + "<" + "/script>");
</script>
<![endif]-->
<script type="text/javascript">
    if("ontouchend" in document) document.write("<script src='${staticDomain}/statics/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
</script>
<script src="${staticDomain}/statics/assets/js/bootstrap.min.js"></script>
<script src="${staticDomain}/statics/assets/js/typeahead-bs2.min.js"></script>
<script src="${staticDomain}/statics/assets/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="${staticDomain}/statics/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
<script src="${staticDomain}/statics/assets/js/jqGrid/i18n/grid.locale-en.js"></script>
<script src="${staticDomain}/statics/assets/js/ace-elements.min.js"></script>
<script src="${staticDomain}/statics/assets/js/ace.min.js"></script>
<script src="${staticDomain}/statics/tool.js"></script>
</#noparse>
<script type="text/javascript">
        var searchUrl = "<#noparse>${mainDomain}</#noparse>/${lowClassName}/select${className}PgByModel";
        var saveUrl = "<#noparse>${mainDomain}</#noparse>/${lowClassName}/save${className}";
        var delUrl = "<#noparse>${mainDomain}</#noparse>/${lowClassName}/del${className}ById";
        function searchJqGrid(){
	        var param = {type:$('#dataType').val()};
	        $("#grid-table").jqGrid('clearGridData');
	        $("#grid-table").jqGrid('setGridParam',{
	        	datatype:'json',
	            postData:param, //发送数据  
	            page:1  
	        }).trigger("reloadGrid");
	    }
        var grid_selector = "#grid-table";
        var pager_selector = "#grid-pager";
        jQuery(grid_selector).jqGrid({
         	caption: "信息列表",
         	url:searchUrl,
         	datatype:'json',
            height: 'auto',
            autowidth: true,
            viewrecords : true,
            rowNum:10,
            rowList:[10,20,30],
            altRows: true,
            multiselect: true,
            multiboxonly: true,
            jsonReader : {  
            	root: "rows",
				page: "page",
				total: "total",
				records: "records",
				repeatitems: false 
        	},  
        	pager : pager_selector,
            colNames:[ <#list feilds as fl>'${fl.mname}',</#list>'操作'],
            colModel:[
                <#list feilds as fl>
      			<#noparse>{</#noparse>name:'${fl.mname}',index:'${fl.mname}'<#noparse>}</#noparse>,
                </#list>
                {name:'myac',index:'', fixed:true, sortable:false, resize:false,
                    formatter:'actions',
                    formatoptions:{
                        keys:true,
                        delOptions:{
                            recreateForm: true, 
                            closeAfterEdit:true,
                            beforeShowForm:beforeDeleteCallback,
                            url:delUrl
                        },
                        editformbutton:true, 
                        editOptions:{
                            recreateForm: true,
                            closeAfterEdit:true, 
                            beforeShowForm: beforeEditCallback
                        }
                    }
                }
            ],
            loadComplete : function() {
                var table = this;
                setTimeout(function(){
                    styleCheckbox(table);
                    updateActionIcons(table);
                    updatePagerIcons(table);
                    enableTooltips(table);
                }, 0);
            },
            gridComplete: function () {
			    removeHorizontalScrollBar(grid_selector);
			},
            editurl: saveUrl
        });
        
		// 分页导航栏位
        jQuery(grid_selector).jqGrid('navGrid',pager_selector,
            { 	
                edit: true,
                editicon : 'icon-pencil blue',
                add: true,
                addicon : 'icon-plus-sign purple',
                del: true,
                delicon : 'icon-trash red',
                refresh: true,
                refreshicon : 'icon-refresh green'
            },
            {
                //编辑
                recreateForm: true, // 新表单
                closeAfterEdit:true, // 是否自动关闭
				closeOnEscape: true, 
                beforeShowForm : function(e) {
                    var form = $(e[0]);
                    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                    style_edit_form(form);
                },
                url:saveUrl
            },
            {
                //新增
                closeAfterAdd: true,
                recreateForm: true,
                viewPagerButtons: false,
                editData:{id:null},
                beforeShowForm : function(e) {
                    var form = $(e[0]);
                    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                    style_edit_form(form);
                },
                url:saveUrl
            },
            {
                //删除
                recreateForm: true,
                beforeShowForm : function(e) {
                    var form = $(e[0]);
                    if(form.data('styled')) return false;
                    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                    style_delete_form(form);
                    form.data('styled', true);
                },
                onClick : function(e) {
                   
                },
                url:delUrl
            }
        )
</script>
</body>
</html>




