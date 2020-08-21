var client=null;

var chat={
    init:function(){
        var _this=this;
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
        var name=$('#userName').text();
        if (message.type === 'newUser') {
               message.content="새로운 유저 입장 "+message.sender;
        }else if (message.type === 'Leave') {
               message.content=message.sender+" 님이 퇴장하셨습니다.";
        }
        var msg="";
        if(message.sender==name){
            msg+='<div class="card chat-message-my">';
        }else{
             msg+='<div class="card chat-message-other">';
        }
        msg+='<div class="card-body">'+
                  '<span class="card-title chat-message-name">'+message.sender+'</span>'+
                  '<span class="chat-createdDate">'+message.createdDate+'</span>'+
                  '<p class="card-text chat-message-content">'+message.content+'</p>'+
            '</div>'+
        '</div>';

        msgList.prepend(msg);
    },
    enterkey:function(){
         _this=this;
            if(window.event.keyCode==13&&$('#message').val()!=""){
                _this.sendMessage();
                $('#message').val("");
            }
    },
    sendMessage:function(){
        var messageContent = $('#message').val();
        var name=$('#userName').text();
        var roomKey=$('#roomKey').val();
        var createdDate=new Date().toLocaleTimeString();
        if(messageContent&&client){
            var chatMessage = {
                roomKey:roomKey,
            	sender : name,
            	content : messageContent,
            	createdDate:createdDate,
            	type : 'CHAT'
            };

            client.send("/app/sendMessage/"+roomKey, {}, JSON
            				.stringify(chatMessage));
            $('#message').val('');
        }
    },
    inviteToChatRoom:function(name){
        var data={
            name:name,
            roomKey:$('#roomKey').val()
        }
         $.ajax({
            url:'/api/v1/invite',
            type:'post',
            dataType:'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
          }).done(function(response){
             $('#'+name).text('요청완료');
              $('#'+name).attr('disabled',true);
          }).fail(function(error){
              $('#'+name).text('초대실패');
          });
    },
    findFriend:function(){
        var friendList=$('.friends');

        if(window.event.keyCode==13){
            $('.friends').empty();
            $.ajax({
                url:'/api/v1/search?name='+$('#inviteName').val(),
                type:'get',
                dataType:'json',
                contentType:'application/json; charset=utf-8'
            }).done(function(response){
                var data=response.data;
                if(data.length==0){
                    friendList.append('<div>'+'없는 이름입니다'+'</div>');
                }else{
                    data.forEach(function(value){
                        var str="";
                        str+='<div class="card col-12">'+
                             '<div class="card-body">'+
                                 '<h4 class="friend-name">'+value.name+'</h4>'+
                         '<button type="button" class="float-right btn btn-outline-success" id='+value.name+' onClick="chat.inviteToChatRoom(\''+value.name+'\')">초대하기</button>'+
                              '</div>'+
                         '</div>';
                         friendList.append(str);
                    });
                    $('.friends').show('show');
                }
            }).fail(function(error){

            });
        }
    }

};

chat.init();