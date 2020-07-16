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
        var roomKey=$('#roomKey').val();
        var name=$('#userName').val();
        client.subscribe('/topic/'+roomKey, function(data){
            _this.onMessageReceived(data);
        });
        client.send("/app/newuser/"+roomKey, {}, JSON.stringify({
        	sender : name,
        	type : 'newUser'
        }))
    },
    onMessageReceived:function(data){
        var message = JSON.parse(data.body);
        var msgList=$('#chat-message-list');

        if (message.type === 'newUser') {
               message.content="새로운 유저 입장 "+message.sender;
        }else if (message.type === 'Leave') {
               message.content=message.sender+" 님이 퇴장하셨습니다.";
        }
        var msg='<div class="card chat-message-my">'+
                    '<div class="card-body">'+
                        '<h5 class="card-title chat-message-name">'+message.sender+'</h5>'+
                        '<p class="card-text chat-message-content">'+message.content+'</p>'+
                    '</div>'+
                '</div>';
        msgList.prepend(msg);
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
        var name=$('#userName').text();
        var roomKey=$('#roomKey').val();
        if(messageContent&&client){
            var chatMessage = {
            	sender : name,
            	content : messageContent,
            	type : 'CHAT'
            };

            client.send("/app/sendMessage/"+roomKey, {}, JSON
            				.stringify(chatMessage));
            $('#message').val('');
        }
    }

}

chat.init();