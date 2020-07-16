var chatroom={
    init:function(){
        _this=this;
        $('#btn-chatRoomCreate').on('click',function(){
            _this.create();
        });
    },
    create:function(){
        var data={
            title:$('#chatRoomTitle').val()
        }
        $.ajax({
            url:'/api/chatroom',
            type:'post',
            dataType:'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(response){
             alert('채팅방생성 완료.');
             window.location.href='/chatroom/'+response.roomKey;
         }).fail(function(error){
             alert('생성실패');
         });
    }
}
chatroom.init();