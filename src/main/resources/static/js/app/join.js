var authString;
var join={
    init:function(){
        var _this=this;
        $('#btn-join').on('click',function(){
            _this.create();
        });
        $('#btn-login').on('click',function(){
             _this.login();
        });


    },
    create:function(){
        var data={
            name:$('#name').val(),
            email:$('#email').val(),
            password:$('#password').val()
        }
        $.ajax({
            type:'POST',
            url:'/join',
            dataType:'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('회원가입 완료. 이메일 인증시 정회원으로 전환됩니다.');
             window.location.href='/login';
        }).fail(function(error){
            alert('error 다시 시도해주십시오 '+error);
        });
    },
    login : function(){
        var data={
             email:$('#email').val(),
             password:$('#password').val()
         }
                $.ajax({
                    type:'POST',
                    url:'/login',
                    dataType:'json',
                    contentType:'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }).done(function(){
                    alert('로그인성공.');
                     window.location.href='/';
                }).fail(function(error){
                    alert('error 다시 시도해주십시오 '+error);
                });
    }
}
join.init();