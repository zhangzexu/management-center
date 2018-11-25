//引入自定义插件
var $;
layui.config({
    base: '/js/extend/' //设置自定义插件路径:根据实际路径进行设置
}).extend({
    tableSelect:'tableSelect'
}).use(['tableSelect'], function() {
    var tableSelect = layui.tableSelect;
    //初始化姓名输入提示框
    tableSelect.render({
        elem: "#parentIdName", //设置容器唯一id
        checkedKey: 'name', //表格的唯一建值，非常重要，影响到选中状态 必填
        searchKey: 'name',	//搜索输入框的name值 默认keyword
        searchPlaceholder: '权限名称搜索',	//搜索输入框的提示文字 默认关键词搜索
        table:{
            url: '/page/user/permissionList.json?page=1&limit=1000&resourceType=menu',
            cols: [
                [{ type: 'radio'
                }, {
                    field: 'name',
                    title: '权限名称',
                    width: 100
                }, {
                    field: 'permission',
                    title: '权限',
                    width : 100

                }, {
                    field: 'url',
                    title: '资源路径',
                    width : 300
                }
                ]]
        }, done: function (elem, data) {
            var NEWJSON = []
            layui.each(data.data, function (index, item) {
                NEWJSON.push(item.name)
            })
            elem.val(NEWJSON.join(","))
        }



    });

});


layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer;

    $ = layui.jquery;
    form.on("submit(addPermission)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        if(data.field.available==="on"){
            data.field.available =false;
        }else data.field.available = true;
        if(data.field.id===''){
            delete data.field.id;
            sysPermissionPatentId(data,index)

            // sysPermissionAdd(data,index);
        }else {
            sysPermissionUpdate(data,index)
        }

        return true;
    })


    function sysPermissionAdd(dataValue,index){
        $.ajax({
            type: 'POST',
            url: '/page/user/permissionAdd.json',
            contentType : 'application/json',
            data: JSON.stringify(dataValue.field),
            async :false,
            success: function(data){
                if(data.rspCode=='000000'){
                    top.layer.alert(data.rspMsg,{icon:6});
                    layer.closeAll("iframe");
                    parent.location.reload();
                }
                else {
                    top.layer.alert(data.rspMsg,{icon:5});
                }
                top.layer.close(index);

            },

            error : function(XMLHttpRequest, textStatus, errorThrown) {
                layer.alert(textStatus, {icon: 5});
                top.layer.close(index);
            }

        });
    }

    function sysPermissionUpdate(dataValue,index) {
        $.ajax({
            type: 'POST',
            url: '/page/user/permissionUpdate.json',
            contentType : 'application/json',
            dataType : 'json',
            async :false,
            data: JSON.stringify(dataValue.field),
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                layer.alert(textStatus, {icon: 5});
                top.layer.close(index);
            },
            success: function(data){
                if(data.rspCode=='000000'){
                    top.layer.alert(data.rspMsg,{icon:6});
                    layer.closeAll("iframe");
                    parent.location.reload();
                }
                else {
                    top.layer.alert(data.rspMsg,{icon:5});
                }
                top.layer.close(index);

            }
        });
    }


    function sysPermissionPatentId(dataValue,index) {
        if(dataValue.field.parentIdName==""){
            dataValue.field.parentId = 0;
            sysPermissionAdd(dataValue,index);
            return;
        }
        $.ajax({
            type: 'POST',
            url: '/page/user/permissionSelectParentId.json',
            data: {name:dataValue.field.parentIdName},
            async :false,
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                layer.alert(textStatus, {icon: 5});
                top.layer.close(index);
            },
            success: function(data){
                if(data.rspCode=='000000'){
                    dataValue.field.parentId = data.data;
                    sysPermissionAdd(dataValue,index);
                }
                else {
                    dataValue.field.parentId = 0;
                    sysPermissionAdd(dataValue,index);
                }

            }
        });
    }



})

Vue.http.options.emulateJSON = true;
var permissionListPage= new Vue({
    el:'#parentId',
    data:{
    },
    created: function () {
        var parentIdName= $("#parentIdName").val();
        console.log(parentIdName+"------")
        this.$http.post('/page/user/permissionSelectParentName.json',{id:parentIdName}).then(function (response) {
            $("#parentIdName").val(response.data.data.name);
        })
    }
});