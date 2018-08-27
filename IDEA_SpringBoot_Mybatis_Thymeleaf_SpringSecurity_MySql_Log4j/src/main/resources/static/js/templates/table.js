var table = $('#example1').DataTable({
    'searching'   : false,
    "sPaginationType": "full_numbers",
    "dom": 'lfrti<"toolbar">p', //自定义工具栏
    "aLengthMenu":[10,20,50,100,200,500],
    /*是否显示排序按钮*/
    "bSort": false,
     "scrollY": 380, //表格页面的长度
     "scrollX": true  , //是否支持滚动
    // 'autoWidth'   : false
})
$("div.toolbar").html('<label style="font-weight: normal;">跳转第 &nbsp;</label>' +
    '<input id="searchNumber" class="form-control input-sm" />' +
    '<label style="font-weight: normal;"> &nbsp;&nbsp;页</label>');
//配置表格需要什么内容
// $('#example2').DataTable({
//   'paging'      : true,
//   'lengthChange': false,
//   'searching'   : false,
//   'ordering'    : true,
//   'info'        : true,
//   'autoWidth'   : false
// })
//绘制的时候触发，绑定文本框的值
table.on('draw.dt', function (e, settings, data) {
    var info = table.page.info();
    //此处的page为0开始计算
    console.info('Showing page: ' + info.page + ' of ' + info.pages);
    $('#searchNumber').val(info.page + 1);
});
//监听文本框更改
$('#searchNumber').change(function () {
    var page = $(this).val();
    page = parseInt(page) || 1;
    page = page - 1;

    //调转到指定页面索引 ，注意大小写
    var oTable = $('#example1').dataTable();
    oTable.fnPageChange(page);
});

// $("#modal-default").modal({backdrop: 'static'});