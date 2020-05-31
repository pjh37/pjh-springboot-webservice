var comment={
    init:function(){
        var _this=this;
        $('#btn-comment-save').on('click',function(){
            _this.save();
        });
    },
    save:function(){
        var data={
            postId:$('#postId').val(),
            content:$('#comment-content').val()
        }
       $.ajax({
            type:'POST',
            url:'/api/comment',
            dataType:'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
       }).done(function(){
            alert('댓글이 등록됬습니다.');
            window.location.href='/post/read/'+$('#groupId').val()+'/'+$('#postId').val();
        }).fail(function(error){
            alert('게시글 등록실패');
       });
    }
}

function commentView(id){
    $('.comment_'+id).toggle('fast');
}

comment.init();