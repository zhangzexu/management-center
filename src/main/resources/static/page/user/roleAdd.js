layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(addRole)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        if(data.field.available==="on"){
            data.field.available =false;
        }else data.field.available = true;
        if(data.field.uid===''){
            delete data.field.uid;
            sysRoleAdd(data,index);
        }else {
            sysRoleUpdate(data,index)
        }

        return true;
    })


    function sysRoleAdd(dataValue,index){
        $.ajax({
            type: 'POST',
            url: '/page/user/roleAdd.json',
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

    function sysRoleUpdate(dataValue,index) {
        $.ajax({
            type: 'POST',
            url: '/page/user/roleUpdate.json',
            contentType : 'application/json',
            dataType : 'json',
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



})