var comment={
    init:function(){
        var _this=this;
        $('#btn-comment-save').on('click',function(){
            _this.save();
        });
        $('.comment_reply_list').hide();


    },
    save:function(){
        var data={
            name:$('#comment_userName').val(),
            postId:$('#postId').val(),
            parentId:-1,
            content:$('#comment-content').val()
        }
       $.ajax({
            type:'POST',
            url:'/api/comment',
            dataType:'text',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
       }).done(function(){
            alert('댓글이 등록됬습니다.');
            window.location.href='/post/read/'+$('#groupId').val()+'/'+$('#postId').val();
        }).fail(function(error){
            alert('댓글 등록실패');
       });
    },
    update:function(commentId){
        postId:$('#postId').val()
        var data={
              commentId:commentId,
              content:$('#comment-content').val()
        }
        $.ajax({
              type:'PUT',
              url:'/api/comment',
              dataType:'json',
              contentType:'application/json; charset=utf-8',
              data: JSON.stringify(data)
        }).done(function(){
                alert('댓글이 수정됬습니다.');
                window.location.href='/post/read/'+$('#groupId').val()+'/'+$('#postId').val();
        }).fail(function(error){
                alert('댓글 수정실패');
        });
    },
    delete:function(commentId){

        var data={
              commentId:commentId,
        }
        $.ajax({
              type:'DELETE',
              url:'/api/comment',
              dataType:'json',
              contentType:'application/json; charset=utf-8',
              data: JSON.stringify(data)
        }).done(function(){
              alert('댓글이 삭제됬습니다.');
              window.location.href='/post/read/'+$('#groupId').val()+'/'+$('#postId').val();
        }).fail(function(error){
              alert('댓글 삭제실패');
        });
    },
    comment_reply_save:function(id){

        var parentId=id;
        var data={
            name:$('#comment_userName').val(),
            postId:$('#postId').val(),
            parentId:id,
            content:$('#comment_reply_'+id).val()
        }
        $.ajax({
            type:'POST',
            url:'/api/comment',
            dataType:'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('답글을 썼습니다.');
            window.location.href='/post/read/'+$('#groupId').val()+'/'+$('#postId').val();
        }).fail(function(){
            alert('답글 생성 실패');
        });
    },
    comment_reply_cancel:function(id){
        $('#comment_'+id).toggle('fast');
    },
    comment_reply_show:function(id){
        $('#comment_reply_list_'+id).toggle('fast');
        /*
        $.ajax({
            type:'GET',
            url:'/api/comment/'+id,
            dataType:'json',
            contentType:'application/json;charset=utf-8',
            success:function(comments){

                comments.forEach(function(comment,idx){

                    $('#comment_reply_list_'+id).append(comment.content);
                })
            }
        });
        */
    },
    like:function(id){
        var likeCount=$('#likeCount_'+id).text()*1+1;
        $('#likeCount_'+id).text(likeCount);
        $.ajax({
            type:'POST',
            url:'api/comment/'+id,
            dataType:'json',
            contentType:'application/json;charset=utf-8'
        }).done(function(){
            //완료
        })
    },
    dislike:function(id){
        var likeCount=$('#dislikeCount_'+id).text()*1+1;
        $('#likeCount_'+id).text(likeCount);
        $.ajax({
             type:'POST',
             url:'api/comment/'+id,
             dataType:'json',
             contentType:'application/json;charset=utf-8'
        }).done(function(){
              //완료
         })
    }
};

function commentView(id){
    $('#comment_'+id).toggle('fast');
}

comment.init();