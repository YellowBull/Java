# down.js 用于发送请求下载指定文件名的文件

```JacaScript
//导出	
$("#exportBar1").click(function(){	
	var queryParams = $('#tt').datagrid('options').queryParams;
	queryParams.exportExcel="1";
	queryParams.page=$('.pagination-num').val();
	queryParams.rows=$(".pagination-page-list").val();
	$.ajax({
		type : "POST",
		async : false,
		url : rootPath + '/tbox/manager/findAllEyTboxForPageList.mvc',
		data : queryParams,
		//dataType : 'json',
		success : function(data) {
			disLoad();
			$.messager.alert('提示', '数据导出成功');	
			
		            var elemIF = document.createElement("iframe");  
		            var data = parseJson(data);
		            elemIF.src = rootPath+ "/tbox/manager/downloadExcel.mvc?filename="+data.filename;   

		            elemIF.style.display = "none";   
		            document.body.appendChild(elemIF);   
		},
		error : function() {
			alert("数据导出失败！");
		}
	});
	return true;

});	


// 上传
function importFile(){
	$("#importForm").form('submit',{
		url:rootPath+'/tbox/manager/addEyTboxsByExcel.mvc',
//		dataType:'json',
//		onSubmit: function(param){
//			return $(this).form('validate');
//		},
		success: function(data){
			try {
				var data = parseJson(data);
					if(data.flag=="1"){
						$.messager.show({
							title:'提示',
							msg:data.msg,
							timeout:5000,
							showType:'slide'
						});
						$('#dlgImport').dialog('close');
						$('#tt').datagrid("reload");
						$('#tt').datagrid("clearSelections");
					} else
						$.messager.alert('提示',data.msg);
				
			} catch (e) {
				$.messager.alert('提示','登录超时或遇到错误，请刷新页面后重试或联系管理员！');
			}
		}
	});
	
	
//	
}
```