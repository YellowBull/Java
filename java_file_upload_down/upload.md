# upload.html 上传文件的模态框

```Html
 <td align="right" valign="middle">
	<a id="importBar1" href="javascript:void(0)"  class="easyui-linkbutton" iconCls="icon-cut"><nobr>导入</nobr></a>
	<a id="exportBar1" href="javascript:void(0)"  class="easyui-linkbutton" iconCls="icon-print"><nobr>导出</nobr></a>
</td> 

<!-- 导入模态框dialog-->
 	<div id="dlgImport" class="easyui-dialog" style="width:300px;height:160px;padding:1px" data-options="maximizable:true,closed:true,resizable:false" >
		<form id="importForm" action="" method="post" enctype="multipart/form-data">
			<table id="importTable" class="insertOrUpdateTable" style="text-align:center !important;padding=5px;" >
					<tr>	
						<td></td>	
						<td width="100px" height="30px"><input style="padding-left:40px;padding-top:30px;" type="file" class="easyui-filebox" id="fileImport" name="fileImport"></td>	
					</tr>
					<!-- <tr>
						<td class="tdTitle" width="100px" height="60px"><a href="javascript:void(0)" class="easyui-linkbutton"
							onClick="sureImport()"><nobr>确定</nobr></a></td>					
						<td class="tdTitle" width="100px" height="60px"><a href="javascript:void(0)" class="easyui-linkbutton"
							onClick="cancelImport()"><nobr>确定</nobr></a></td>	
					</tr> -->
			</table>
		</form> 
	</div>
```