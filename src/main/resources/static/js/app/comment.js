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
        postId:$('#postId').val();
        content=$('#comment_updated_content_'+commentId).val();
        var spinner=$('#comment_content_spinner_'+commentId);
        var originalContent=$('comment_content_'+commentId);
        spinner.show();
        originalContent.hide();
        var data={
              commentId:commentId,
              content:content
        }
        $.ajax({
              type:'PUT',
              url:'/api/comment',
              dataType:'json',
              contentType:'application/json; charset=utf-8',
              data: JSON.stringify(data)
        }).done(function(){
                spinner.hide();
                $('#comment_content_'+commentId).text(content);
                $('#comment_updated_content_'+commentId).val('');
                $('#comment_update_'+commentId).toggle('fast');
                //alert('댓글이 수정됬습니다.');
                //window.location.href='/post/read/'+$('#groupId').val()+'/'+$('#postId').val();
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
    comment_reply_save:function(parentId,childId){
        var data={
            name:$('#comment_userName').val(),
            postId:$('#postId').val(),
            parentId:parentId,
            content:$('#comment_reply_'+childId).val()
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
        if($('#comment_reply_list_'+id).data("isClicked")===true){
            return;
        }
        $('#comment_reply_list_'+id).data("isClicked",true);
        var list=$('#comment_reply_list_'+id);
        var spinner=$('#comment_content_spinner_'+id);
        spinner.show();

        $.ajax({
            type:'GET',
            url:'/api/comment/'+id,
            dataType:'json',
            contentType:'application/json;charset=utf-8',
            success:function(comments){

                comments.forEach(function(comment,idx){
                    var data='<div class="comment-item">'+
                                 '<div class="comment-header">'+
                                      '<span class="userName">'+comment.name+'</span>'+
                                       '<span class="modifiedDate">'+comment.modifiedDate+'</span>'+
                                       '<th:block th:if="${account.name.equals(child.name)}">'+
                                             '<a href="javascript:;" class="btn-comment-delete" onclick="comment.delete(\''+comment.id+'\')">삭제</a>'+
                                             '<a href="javascript:;" class="btn-comment-update" onclick="commentUpdateView(\''+comment.id+'\')">수정</a>'+
                                        '</th:block>'+
                                  '</div>'+
                                  '<div class="comment-body">'+
                                       '<p>'+comment.content+'</p>'+
                                  '</div>'+
                                   '<div class="comment-footer">'+
                                         '<a href="javascript:;" class="btn-like" onclick="comment.like(\''+comment.id+'\')">'+
                                               '<img src="/images/like.png">'+
                                         '</a>'+
                                                   '<span id=likeCount_'+comment.id+'>'+comment.likeCount+'</span>'+
                                                   '<a href="javascript:;" class="btn-dislike" onclick="comment.dislike(\''+comment.id+'\')">'+
                                                       '<img src="/images/dislike.png">'+
                                                   '</a>'+
                                                   '<span id=dislikeCount_'+comment.id+'>'+comment.dislikeCount+'</span>'+

                                                   '<a href="javascript:;" onclick="commentView(\''+comment.id+'\')">답글</a>'+
                                                   '<div class="re-comment" id=comment_'+comment.id+'>'+
                                                       '<textarea id=comment_reply_'+comment.id+' class="form-control" placeholder="공개 답글 추가..." maxlength="10000"></textarea>'+
                                                       '<div class="comment-reply-btn">'+
                                                           '<button type="button" class="btn btn-secondary" onclick="comment.comment_reply_cancel(\''+comment.id+'\')">취소</button>'+
                                                           '<button type="button" class="btn btn-primary" onclick="comment.comment_reply_save(\''+id+'\',\''+comment.id+'\')">등록</button>'+
                                                       '</div>'+
                                                   '</div>'+
                                                   '<div class="comment-update-dialog" id=comment_update_'+comment.id+'>'+
                                                       '<textarea id=comment_updated_content_'+comment.id+' class="form-control" placeholder="공개 답글 추가..." maxlength="10000"></textarea>'+
                                                       '<div class="comment-reply-btn">'+
                                                           '<button type="button" class="btn btn-secondary" onclick="comment.comment_update_cancel(\''+comment.id+'\')">취소</button>'+
                                                           '<button type="button" class="btn btn-primary" onclick="comment.update(\''+comment.id+'\')">수정</button>'+
                                                       '</div>'+
                                                   '</div>'+
                                   '</div>'+
                               '</div>';

                    $('#comment_reply_list_'+id).append(data);
                })
                spinner.hide();
            }
        });

    },
    comment_update_cancel:function(id){
        $('#comment_update_'+id).toggle('fast');
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
function commentUpdateView(id){
    $('#comment_update_'+id).toggle('fast');
}
comment.init();