var body;

layui.config({
    base: '/js/extend/',
}).extend({
    treeGrid:'treeGrid'
}).use(['form','layer','treeGrid','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        treeGrid = layui.treeGrid;

    //用户列表
    var tableIns = treeGrid.render({
        elem: '#permissionList',
        url : '/page/user/permissionList.json',
        method: 'post',
        cellMinWidth : 100
        ,idField:'id'//必須字段
        ,treeId:'id'//树形id字段名称
        ,treeUpId:'parentId'//树形父id字段名称
        ,treeShowName:'name',//以树形式显示的字段
        isOpenDefault:true,//节点默认是展开还是折叠【默认展开】
        page : true,
        height : "full-125",
        limit : 10000,
        id : "permissionListTable",
        cols : [[
            {type: "checkbox",  width:50},
            {title:'ID' ,type:'numbers', width:80   },
            {field: 'name', title: '权限名称', minWidth:200, align:"center"},
            {field: 'permission', title: '权限', minWidth:100, align:'center'},
            {field: 'resourceType', title: '资源类型', minWidth:100, align:'center'},
            {field: 'url', title: '资源路径', minWidth:200, align:'center'},
            {field: 'available', title: '角色状态',  align:'center',templet:function(d){
                return '<input type="checkbox" name="available" value=\''+JSON.stringify(d)
                    +'\' title="锁定"' +
                    'lay-filter="lockAvailable" '+(d.available == false ? "checked" : "" )+' >';
            }},
            {field: 'descInfo', title: '备注',minWidth:200, align:"center"},
            {field: 'createDate', title: '创建时间', align:'center',minWidth:150},
            {field: 'lastModifiedDate', title: '修改时间', align:'center',minWidth:150},
            {title: '操作', minWidth:175,align:"center", templet:function () {
                    return ' <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="add">添加</a><a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a><a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>'
                }}
        ]]
    });

    form.on('checkbox(lockAvailable)',function (obj) {
       var dataValue = JSON.parse(this.value);
        dataValue.available = !obj.elem.checked;
        delete  dataValue.LAY_INDEX;
        delete  dataValue.LAY_TABLE_INDEX;
        sysPermissionUpdate(JSON.stringify(dataValue));
    });




    $(".addNews_btn").click(function(){
        add();
    })

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        query();
    });

    function query() {
        var available =$("#available option:selected").val();
        var resourceType =$("#resourceType option:selected").val();
        var name = $("#name").val();
        var permission = $("permission").val();
        treeGrid.query('permissionListTable',{
            where:{
                name:''
            }
        });
    }

    //
    // //列表操作
    treeGrid.on('tool(permissionList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            add(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此权限,同时会删除子权限,该操作不可回退？',{icon:3, title:'提示信息'},function(index){
                sysPermissionDelete(data.id,index);
            });
        }else if(layEvent==='add'){
             addSon(data)
        }

    });
    function addSon(edit){
        var index = layui.layer.open({
            title : "添加子权限",
            type : 2,
            content : "permissionAdd.html",
            success : function(layero, index){
                body = layui.layer.getChildFrame('body', index);
                if(edit){
                    sysPermissionSelectParentName(edit.parentId,body);
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
    function add(edit){
        var title="添加权限";
        if(edit){
            title = "修改权限";
        }
        var index = layui.layer.open({
            title : title,
            type : 2,
            content : "permissionAdd.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".id").val(edit.id);
                    body.find(".name").val(edit.name);
                    body.find(".permission").val(edit.permission);
                    var type=['<option value="menu">menu</option>','<option value="button">button</option>','<option value="link">link</option>'];
                    var ht = "";
                    for(var i = 0 ;i<type.length;i++){
                        if(type[i].indexOf(edit.resourceType)!=-1){
                            ht+=type[i];
                            type[i] = type[type.length-1];
                            type.pop();
                        }
                    }
                    for(var i = 0 ;i<type.length;i++){
                        ht+=type[i];
                    }
                    var resourceTypeHtml='<select id="resourceType" name="resourceType">\n' +
                        ht+
                        '</select>';
                    // console.log(resourceTypeHtml);
                    body.find(".resourceType").html(resourceTypeHtml);
                    body.find(".parentIdName").val(edit.parentId);
                    // sysPermissionSelectParentName(edit.parentId,body);
                    body.find(".available").html('<input type="checkbox" name="available" class="layui-input" '+(edit.available == false ? "checked" : "" )+' title="锁定">');  //是否可用
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

    function sysPermissionDelete(dataValue,index) {
        $.ajax({
            type: 'POST',
            url: '/page/user/permissionDelete.json',
            data: {'dataValue':dataValue},
            async :false,
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

    function sysPermissionSelectParentName(dataValue,body) {
        $.ajax({
            type: 'POST',
            url: '/page/user/permissionSelectParentName.json',
            data: {id:dataValue},
            async :false,
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                layer.alert(textStatus, {icon: 5});
            },
            success: function(data){
                if(data.rspCode=='000000'){
                    body.find(".parentIdName").val(data.data.name);
                    form.render();

                }
                else {
                }

            }
        });
    }


    function sysPermissionDelete(dataValue,index) {
        $.ajax({
            type: 'POST',
            url: '/page/user/permissionDelete.json',
            data: {'dataValue':dataValue},
            async :false,
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

    function sysPermissionUpdate(dataValue) {
        $.ajax({
            type: 'POST',
            url: '/page/user/permissionUpdate.json',
            contentType : 'application/json',
            dataType : 'json',
            data: dataValue,
            async :false,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                layer.alert(textStatus, {icon: 5});
            },
            success: function (data) {
                query()
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



