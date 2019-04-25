<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript">
    $(function () {

        var tb = [{
            iconCls: 'icon-add',
            text: '注册',
            handler: function () {
                $('#dd_user').dialog('open');

            }
        }, '-', {
            iconCls: 'icon-edit',
            text: '冻结',
            handler: function () {
                var selected = $('#dg_user').edatagrid("getSelected");
                if (selected) {

                    $.ajax({
                        url: "${pageContext.request.contextPath}/user/updateStatus",
                        data: "id=" + selected.id + "&status=" + selected.status,
                        success: function () {
                            $('#dg_user').edatagrid("load")
                        }
                    })
                }
            }
        }];


        $('#dg_user').edatagrid({
            url: '${pageContext.request.contextPath}/user/selectAll',
            fit: true,
            fitColumns: true,
            pagination: true,
            pageSize: 3,
            pageList: [3, 6, 9],
            columns: [[
                {field: 'name', title: '姓名', width: 100},
                {field: 'dharma', title: '法名', width: 100},
                {
                    field: 'status', title: '状态', width: 100, editor: {
                        type: 'text',
                        options: {required: true}
                    },

                },
                {field: 'createDate', title: '日期', width: 100}
            ]],

            toolbar: tb,


            view: detailview,

            detailFormatter: function (rowIndex, rowData) {

                return '<table><tr>' +
                    '<td rowspan=3 style="border:0"><img src="${pageContext.request.contextPath}/img/user/' + rowData.headImg + '" style="height:100px;"></td>' +
                    '<td style="border:0">' +
                    '<p>法名: ' + rowData.dharma + '</p>' +
                    '<p>地址: ' + rowData.province + '--' + rowData.city + '</p>' +
                    '<p>电话: ' + rowData.phone + '</p>' +
                    '<p>签名: ' + rowData.sign + '</p>' +
                    '</td>' +
                    '</tr></table>';
            }
        });
    })

    function addUser() {
        $('#ff').form('submit', {
            url: '${pageContext.request.contextPath}' + '/user/insert',
            onSubmit: function () {

            },
            success: function (data) {
                $("#dg_user").edatagrid("load");
            }
        });

    }

</script>
<%--展示--%>
<table id="dg_user"></table>
<%--注册--%>
<div id="dd_user" class="easyui-dialog" title="注册" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addUser();
				    $('#dd_user').dialog('close');
				}
			},{
				text:'关闭',
				handler:function(){
				    $('#dd_user').dialog('close');
				}
			}]">

    <form id="ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="name">姓名:</label>
            <input id="name" class="easyui-validatebox" type="text" name="name" data-options="required:true"/>
        </div>
        <div>
            <label for="dharma">法名:</label>
            <input id="dharma" class="easyui-validatebox" type="text" name="dharma" data-options="required:true"/>
        </div>
        <div>
            <label for="password">密码:</label>
            <input id="password" class="easyui-validatebox" type="text" name="password" data-options="required:true"/>
        </div>

    </form>
</div>
