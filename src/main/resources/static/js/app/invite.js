var invite={
    init:function(){
        _this=this;
        $('#btn-invite').on('click',function(){
            _this.friendInvite();
        });
    },
    friendInvite:function(){

        var data={
            name:$('#inviteName').val()
        }
        $.ajax({
            type:'POST',
            url:'/api/friend/invite',
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
    }
}
invite.init();