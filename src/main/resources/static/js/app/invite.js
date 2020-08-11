var invite={
    init:function(){
        var _this=this;
        $('#btn-invite').on('click',function(){
            _this.friendInvite();
        });

        $('#btn-auth-list').on('click',function(){
            $('#friendRequestAuthWaitModal').modal('show');
            _this.inviteList();
        });
    },
    friendInvite:function(){

        var data={
            name:$('#inviteName').val()
        }
        $.ajax({
            type:'POST',
            url:'/api/v1/friend/invite',
            dataType:'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(res){
            if(res.result){
                alert('초대 요청 전송 완료');
                $('#inviteModal').modal('hide');
            }else{
                alert('없는 아이디 입니다.');
            }
        }).fail(function(error){
            alert('오류발생');
        });
    },
    inviteList:function(){
        $.ajax({
              type:'GET',
              url:'/api/v1/invite/list',
              dataType:'json',
              contentType:'application/json; charset=utf-8'
        }).done(function(res){
            var data=res.data;
            data.forEach(function(value){
                var str="";
                str+="<tr id=data-id-"+value.id+">"+
                        "<td>"+value.name+"</td>"+
                        "<td>"+value.state+"</td>"+
                        "<td>"+
                             "<button type='button' class='btn btn-primary' onClick='invite.inviteConfirm("+value.id+")'>수락</button>"+
                             "<button type='button' class='btn btn-danger' onClick='invite.inviteRefuse("+value.id+")'>거절</button>"+
                        "</td>"
                 "</tr>"

                 $('.inviteList').append(str);
            });
         }).fail(function(error){

         });
    },
    inviteConfirm:function(id){
        $('#data-id-'+id).remove();
        $.ajax({
            type:'POST',
            url:'/api/v1/invite/confirm/'+id,
            dataType:'json',
            contentType:'application/json; charset=utf-8'
        }),done(function(res){

        }).fail(function(error){
            alert('오류발생');
        });
    },
     inviteRefuse:function(id){
        $('#data-id-'+id).remove();
        $.ajax({
            type:'POST',
            url:'/api/v1/invite/refuse/'+id,
            dataType:'json',
            contentType:'application/json; charset=utf-8'
        }),done(function(res){

        }).fail(function(error){
             alert('오류발생');
        });
     }
};
invite.init();