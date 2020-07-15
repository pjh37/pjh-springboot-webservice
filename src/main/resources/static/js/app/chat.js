var client=null;

var chat={
    init:function(){
        _this=this;
        var socket = new SockJS('/websocketApp');
        client = Stomp.over(socket);
        client.connect({}, function(){
            _this.connectionSuccess();
        });
    },
    connectionSuccess:function(){
        _this=this;
        var id=$('#chatRoomId').val();
        var name=$('#userName').val();
        client.subscribe('/topic/'+id, function(data){
            _this.onMessageReceived(data);
        });
        client.send("/app/newuser/"+id, {}, JSON.stringify({
        	sender : name,
        	type : 'newUser'
        }))
    },
    onMessageReceived:function(data){
        var message = JSON.parse(data.body);
        var msgList=$('#chat-message-list');
        if (message.type === 'newUser') {
            msgList.append('<div>'+'새로운 유저 입장 '+message.sender +'</div>');
        }else if (message.type === 'Leave') {
            msgList.append('<div>'+' 유저 떠남 '+message.sender +'</div>');
        }else {
            msgList.append('<div>'+message.content +'</div>');
        }
    },
    enterkey:function(){
         _this=this;
            if(window.event.keyCode==13){
                _this.sendMessage();
                $('#message').val("");
            }
    },
    sendMessage:function(){
        var messageContent = $('#message').val();
        var name=$('#userName').val();
        var id=$('#chatRoomId').val();
        if(messageContent&&client){
            var chatMessage = {
            	sender : name,
            	content : messageContent,
            	type : 'CHAT'
            };

            client.send("/app/sendMessage/"+id, {}, JSON
            				.stringify(chatMessage));
            $('#message').val('');
        }
    }

}
/*
function enterkey(){
    if(window.event.keyCode==13){
        sendMessage('TALK');
        $('#message').val("");
    }
}
*/
chat.init();