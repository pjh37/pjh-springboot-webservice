
var post={
    init:function(){
        var _this=this;
        $('#btn-save').on('click',function(){
            _this.save();
        });
        $('#btn-update').on('click',function(){
            _this.update();
        });
        $('#btn-delete').on('click',function(){
            _this.delete();
        });

    },
    save:function(){
        var data={
            groupId:$('#groupId').val(),
            title:$('#title').val(),
            content:$('#content').val()
        }

        $.ajax({
             type:'POST',
             url:'/api/post',
             dataType:'json',
             contentType:'application/json; charset=utf-8',
             data: JSON.stringify(data)
        }).done(function(){
             alert('게시글이 등록됬습니다.');
             window.location.href='/group/'+$('#groupId').val();
        }).fail(function(error){
             alert('게시글 등록실패');
        });
    },
    update:function(){
        var postId=$('#postId').val();
        var data={
            title:$('#title').val(),
            content:$('#content').val()
        }
        $.ajax({
            type:'put',
            url:'/api/post/'+postId,
            dataType:'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
         }).done(function(){
             alert('게시글이 수정됬습니다.');
             window.location.href='/group/'+$('#groupId').val();
         }).fail(function(error){
             alert('게시글 수정실패');
         });
    },
    delete :function(){
        var postId=$('#postId').val();
        $.ajax({
             type:'delete',
             url:'/api/post/'+postId,
             dataType:'json',
             contentType:'application/json; charset=utf-8'
        }).done(function(){
             alert('게시글이 삭제됬습니다.');
             window.location.href='/group/'+$('#groupId').val();
        }).fail(function(error){
             alert('게시글 삭제실패');
        });
    }
};

post.init();
