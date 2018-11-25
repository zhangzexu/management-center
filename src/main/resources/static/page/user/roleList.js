layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //用户列表
    var tableIns = table.render({
        elem: '#roleList',
        url : '/page/user/roleList.json',
        method: 'post',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        id : "roleListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {title: 'ID', width:80, fixed: 'left' ,type:"numbers"},
            {field: 'role', title: '角色名称', minWidth:150, sort: true,align:"center"},
            {field: 'description', title: '角色描述', minWidth:200, align:'center'},
            {field: 'available', title: '角色状态',  align:'center',templet:function(d){
                return '<input type="checkbox" name="available" value=\''+JSON.stringify(d)
                    +'\' title="锁定"' +
                    'lay-filter="lockAvailable" '+(d.available == false ? "checked" : "" )+' >';
            }},
            {field: 'permissions', title: '权限', align:'center'},
            {field: 'descInfo', title: '备注'},
            {field: 'createDate', title: '创建时间', align:'center',minWidth:150},
            {field: 'lastModifiedDate', title: '修改时间', align:'center',minWidth:150},
            {title: '操作', minWidth:175,fixed:"right",align:"center", templet:function () {
                    return ' <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a><a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>'
                }}
        ]]
    });

    //操作----------------
    table.on('tool(roleList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            add(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
                sysRoleDelete(data.uid,index);
            });
        }
    });


    //改----------------
    form.on('checkbox(lockAvailable)',function (obj) {
       var dataValue = JSON.parse(this.value);
        dataValue.available = !obj.elem.checked;
        delete  dataValue.LAY_INDEX;
        delete  dataValue.LAY_TABLE_INDEX;
        sysRoleUpdate(JSON.stringify(dataValue));
    });

    //查----------------
    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        query();
    });

    function query() {
        var available =$("#available option:selected").val();
        var role = $("#role").val();
        table.reload("roleListTable",{
            where: {
                available:available,
                role:role
            }
        })
    }



   //增-------------

    $(".addNews_btn").click(function(){
        add();
    })

    //批量删除-----------
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('roleListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].uid);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
                sysRoleDelete(newsId.toString(),index)


                // })
            })
        }else{
            layer.msg("请选择需要删除的用户");
        }
    })


    //添加用户
    function add(edit){
        var title="添加角色";
        if(edit){
            title = "修改角色";
        }
        var index = layui.layer.open({
            title : title,
            type : 2,
            content : "/page/user/roleAdd.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    console.log(edit.available);
                    body.find(".uid").val(edit.uid);
                    body.find(".role").val(edit.role);  //角色名
                    body.find(".description").val(edit.description);  //角色简介
                    body.find(".available").html('<input type="checkbox" name="available" class="layui-input" '+(edit.available == false ? "checked" : "" )+' title="锁定">');  //是否可用
                    // body.find(".permissions").val(edit.permissions);  //权限
                    body.find(".descInfo").val(edit.descInfo);    //备注
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })
    }







    //ajax
    //ajax

    function sysRoleDelete(dataValue,index) {
        $.ajax({
            type: 'POST',
            url: '/page/user/roleDelete.json',
            data: {'dataValue':dataValue},
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                layer.alert(textStatus, {icon: 5});
                layer.close(index);
            },
            success: function (data) {
                query()
                if (data.rspCode == '000000') {
                    layer.alert(data.rspMsg, {icon: 6});
                }
                else {
                    layer.alert(data.rspMsg, {icon: 5});
                }
                layer.close(index);


            }
        });
    }

    function sysRoleUpdate(dataValue) {
        $.ajax({
            type: 'POST',
            url: '/page/user/roleUpdate.json',
            contentType : 'application/json',
            dataType : 'json',
            data: dataValue,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                layer.alert(textStatus, {icon: 5});
            },
            success: function (data) {
                query();
                if (data.rspCode == '000000') {
                    layer.alert(data.rspMsg, {icon: 6});
                }
                else {
                    layer.alert(data.rspMsg, {icon: 5});
                }

            }
        });
    }
})
