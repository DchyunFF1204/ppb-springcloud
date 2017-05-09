<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>测试FormData方式上传文件</title>
    <style>
        body{margin: 0;padding: 50px 0 0 0;}
        .topDiv{margin-left: 40%; margin-top: 200px; float: left; position: absolute;}
        .topDiv ul li{list-style: none; margin-top: 10px;}
        .topDiv ul li label{color: #999;}
        .topDiv ul li input{width: 150px;}
    </style>
</head>
<body>
<div class="topDiv" align="center">
    <form id="formData" style="">
        <ul>
            <li>
                <label>图片名称:</label>
                <input type="text" name="name" id="name" value="">
            </li>
            <li>
                <label>选择图片:</label>
                <input type="file" name="fileData" id="file" onchange="onFormPost()" >
                <input id="submit" type="button" value="提交文件" />
            </li>
        </ul>
    </form>
</div>

</body>
<script type="text/javascript" src="../static/jquery-1.10.2.min.js?ver=1"></script>
<script>
 	var myForm=new FormData();
    function onFormPost(){
    	myForm = new FormData($("#formData")[0]);  
    }
    
    $("#submit").on('click', function(){
    	$.ajax({  
            url : '${mainDomain}/uploadAjaxImg',  
            type : 'POST',  
            data : myForm,  
            async : false,  
            cache : false,  
            contentType : false,
            processData : false,
            success : function(data) {  
                alert(data);  
            },  
            error : function(data) {  
                alert(data);  
            }  
        });  
       
    });

</script>
</html>