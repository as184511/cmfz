<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script>
    $(function () {

        var tb = [{
            iconCls: 'icon-tip',
            text: '专辑详情',
            handler: function () {
                var selected = $('#tt_album').treegrid("getSelected");

                if (selected) {
                    if (!selected.duration) {
                        $('#showalbum').dialog('open');
                        $("#aamount").val(selected.amount);
                        $("#aauthor").val(selected.author);
                        $("#aboardcast").val(selected.boardcast);
                        $("#aimgPath").prop("src", "${pageContext.request.contextPath}/img/album/" + selected.imgPath);

                        var child = "<div>";
                        $.each(selected.children, function (index2, second) {
                            console.log(second)
                            child += "<p>" + second.title + "</p>";
                        })
                        child += "</div>";

                        $("#t1").append(child)
                    } else {
                        alert("请先选择一个专辑")
                    }

                } else {
                    alert("请先选择一个专辑")
                }


            }
        }, '-', {
            iconCls: 'icon-add',
            text: '添加专辑',
            handler: function () {
                $('#add_album').dialog('open');
            }
        }, '-', {
            iconCls: 'icon-add',
            text: '添加章节',
            handler: function () {
                var selected = $('#tt_album').treegrid("getSelected");

                if (selected) {
                    if (!selected.duration) {
                        $('#add_chapter').dialog('open');
                        $("[name=album_id]").val(selected.id);

                    } else {
                        alert("请先选择一个专辑")
                    }

                } else {
                    alert("请先选择一个专辑")
                }

            }
        }, '-', {
            iconCls: 'icon-undo',
            text: '下载音频',
            handler: function () {
                var selected = $('#tt_album').treegrid("getSelected");
                if (selected.size) {
                    location.href = "${pageContext.request.contextPath}/chapter/download?loadPath=" + selected.loadPath + "&title=" + selected.title
                }

            }
        }, '-', {
            iconCls: 'icon-undo',
            text: '导出报表',
            handler: function () {
                location.href = "${pageContext.request.contextPath}/album/exportExcel"

            }
        }];

        $('#tt_album').treegrid({
            url: '${pageContext.request.contextPath}/album/selectAll',
            idField: 'id',//用来标识标识树节点   主干树与分支树节点  ID不能有相同  并且使用相同的字段  否则ID冲突
            treeField: 'title',//用来定义树节点   树形表格上要展示的信息   注意使用相同的字段 用来展示对应节点名称
            toolbar: tb,
            fit: true,
            fitColumns: true,
            onDblClickCell: function (field, row) {
                if (row.duration) {

                    var audio = document.getElementById("bgMusic");
                    console.log(row)
                    console.log(field)
                    $("#bgMusic").prop("src", "${pageContext.request.contextPath}/img/chapter/" + row.loadPath)
                    audio.play();

                }
            },
            columns: [[
                {field: 'title', title: '名字', width: 180},
                {field: 'loadPath', title: '下载路径', width: 180},
                {field: 'size', title: '章节大小', width: 60},
                {field: 'duration', title: '章节时长', width: 80}
            ]]
        });

    })

    function addAlbum() {
        $('#ff').form('submit', {
            url: '${pageContext.request.contextPath}' + '/album/insert',
            onSubmit: function () {

            },
            success: function (data) {
                $('#tt_album').treegrid("load");
            }
        });

    }

    function addChapter() {
        $('#toAddChapter').form('submit', {
            url: '${pageContext.request.contextPath}' + '/chapter/insert',
            onSubmit: function () {

            },
            success: function (data) {
                $('#tt_album').treegrid("load");
            }
        });

    }

</script>
<table id="tt_album" style="width:600px;height:400px"></table>
<%--展示专辑详情--%>
<div id="showalbum" class="easyui-dialog" title="专辑详情" style="width:800px;height:500px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'退出',
				handler:function(){
				    $('#showalbum').dialog('close');
				}
			}]">

    <form id="detail">
        <div id="t1">
            章节数量<input id="aamount"><br/>
            作者 <input id="aauthor"><br/>
            播音<input id="aboardcast"><br/>
            <img id="aimgPath" src=""/>
        </div>

    </form>
</div>


<%--添加专辑--%>
<div id="add_album" class="easyui-dialog" title="添加专辑" style="width:800px;height:500px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addAlbum();
				    $('#add_album').dialog('close');
				}
			},{
				text:'关闭',
				handler:function(){
				    $('#add_album').dialog('close');
				}
			}]">

    <form id="ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="title">标题:</label>
            <input id="title" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="amount">数量:</label>
            <input id="amount" class="easyui-validatebox" type="text" name="amount" data-options="required:true"/>
        </div>
        <div>
            <label for="score">评分:</label>
            <input id="score" class="easyui-validatebox" type="text" name="score" data-options="required:true"/>
        </div>
        <div>
            <label for="author">作者:</label>
            <input id="author" class="easyui-validatebox" type="text" name="author" data-options="required:true"/>
        </div>
        <div>
            <label for="boardcast">播音:</label>
            <input id="boardcast" class="easyui-validatebox" type="text" name="boardcast" data-options="required:true"/>
        </div>
        <div>
            <label for="brief">简介:</label>
            <input id="brief" class="easyui-validatebox" type="text" name="brief" data-options="required:true"/>
        </div>
        <div>
            <label for="publishDate">日期:</label>
            <input id="publishDate" class="easyui-validatebox" type="date" name="publishDate"
                   data-options="required:true"/>
        </div>
        选择封面: <input name="photo" class="easyui-filebox" style="width:150px">
    </form>
</div>

<%--添加章节--%>
<div id="add_chapter" class="easyui-dialog" title="添加章节" style="width:600px;height:400px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addChapter();
				    $('#add_chapter').dialog('close');
				}
			},{
				text:'关闭',
				handler:function(){
				    $('#add_chapter').dialog('close');
				}
			}]">

    <form id="toAddChapter" method="post" enctype="multipart/form-data">
        <div>
            <label>标题:</label>
            <input class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <input type="hidden" name="album_id" data-options="required:true"/>
        </div>

        选择章节: <input name="photo" class="easyui-filebox" style="width:150px">
    </form>
</div>


<audio id="bgMusic" src="" controls loop autoplay></audio>