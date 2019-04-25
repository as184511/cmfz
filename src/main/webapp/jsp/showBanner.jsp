<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript">
    $(function () {

        var tb = [{
            iconCls: 'icon-add',
            text: '添加',
            handler: function () {
                $('#dd_banner').dialog('open');

            }
        }, '-', {
            iconCls: 'icon-edit',
            text: '修改',
            handler: function () {
                var selected = $('#dg_banner').edatagrid("getSelected");
                console.log(selected)
                if (selected) {
                    $('#update_banner').dialog('open');
                    $("#id2").val(selected.id);
                    $("#title2").val(selected.title);
                    $("#status2").val(selected.status);

                    $("#creatDate2").val(selected.creatDate);
                    $("#imgPath").prop("src", "${pageContext.request.contextPath}/img/banner/" + selected.imgPath);


                } else {
                    alert("请先选择一个轮播图")
                }
            }
        }, '-', {
            iconCls: 'icon-delete',
            text: '删除',
            handler: function () {
                $('#dg_banner').edatagrid('destroyRow');

            }
        }, '-', {
            iconCls: 'icon-save',
            text: '导出报表',
            handler: function () {
                location.href = "${pageContext.request.contextPath}/banner/exportExcel"
            }
        }];


        $('#dg_banner').edatagrid({
            url: '${pageContext.request.contextPath}/banner/selectAll',
            <%--saveUrl: '${pageContext.request.contextPath}/banner/update',--%>
            destroyUrl: '${pageContext.request.contextPath}/banner/delete',
            fit: true,
            fitColumns: true,
            pagination: true,
            pageSize: 3,
            pageList: [3, 6, 9],
            columns: [[
                {
                    field: 'title', title: '标题', width: 100
                },
                {
                    field: 'status', title: '状态', width: 100, editor: {

                    },
                    formatter: function (value, rowData, rowIndex) {
                        if (value == 0) {
                            return "展示";
                        } else {
                            return "不展示"
                        }
                    }
                },
                {field: 'creatDate', title: '日期', width: 100}
            ]],

            toolbar: tb,


            view: detailview,

            detailFormatter: function (rowIndex, rowData) {

                return '<table><tr>' +
                    '<td rowspan=2 style="border:0"><img src="${pageContext.request.contextPath}/img/banner/' + rowData.imgPath + '" style="height:50px;"></td>' +
                    '<td style="border:0">' +
                    '<p>描述: ' + rowData.title + '</p>' +
                    '<p>状态: ' + rowData.status + '</p>' +
                    '</td>' +
                    '</tr></table>';
            }
        });
    })

    function addBanner() {
        $('#ff').form('submit', {
            url: '${pageContext.request.contextPath}' + '/banner/addBanner',
            onSubmit: function () {

            },
            success: function (data) {
                $("#dg_banner").edatagrid("load");
            }
        });

    }

    /*修改*/

    function updateBanner() {
        $('#update').form('submit', {
            url: '${pageContext.request.contextPath}' + '/banner/update',
            onSubmit: function () {

            },
            success: function (data) {
                $("#dg_banner").edatagrid("load");
            }
        });

    }

</script>
<table id="dg_banner"></table>
<div id="dd_banner" class="easyui-dialog" title="添加" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addBanner();
				    $('#dd_banner').dialog('close');
				}
			},{
				text:'关闭',
				handler:function(){
				    $('#dd_banner').dialog('close');
				}
			}]">

    <form id="ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="title">标题:</label>
            <input id="title" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="creatDate">日期:</label>
            <input id="creatDate" class="easyui-validatebox" type="date" name="creatDate" data-options="required:true"/>
        </div>
        <input name="photo" class="easyui-filebox" style="width:150px">
    </form>
</div>
<%--修改--%>
<div id="update_banner" class="easyui-dialog" title="修改" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    updateBanner();
				    $('#update_banner').dialog('close');
				}
			},{
				text:'关闭',
				handler:function(){
				    $('#update_banner').dialog('close');
				}
			}]">

    <form id="update" method="post" enctype="multipart/form-data">
        <div>
            <div>
                <label for="id2">id:</label>
                <input id="id2" class="easyui-validatebox" type="hidden" name="id" data-options="required:true"/>
            </div>
        </div>
        <div>
            <label for="title2">标题:</label>
            <input id="title2" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="status2">状态:</label>
            <input id="status2" class="easyui-validatebox" type="text" name="status" data-options="required:true"/>
        </div>
        <div>
            <label for="creatDate2">日期:</label>
            <input id="creatDate2" class="easyui-validatebox" type="date" name="creatDate"
                   data-options="required:true"/>
        </div>
        <div>
            <label for="imgPath">图片:</label>
            <img id="imgPath" src="" name="imgPath" style="height:50px;"/>
        </div>
        <input name="photo" class="easyui-filebox" style="width:150px">
    </form>
</div>
