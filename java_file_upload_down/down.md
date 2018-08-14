# down.js 用于发送请求下载指定文件名的文件

```JacaScript
// 下载
elemIF.src = rootPath+ "/cust/downloadExcel.mvc?filename="+"dateActivated.xls"; 


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