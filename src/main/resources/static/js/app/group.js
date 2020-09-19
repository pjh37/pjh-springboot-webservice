var selectGroupId;
var group={
    init:function(){
        var _this=this;
        $('#passwordCheck').change(function(){
            if($('#passwordCheck').is(":checked")){
                   $('#passwordGroup').show();
             }else{
                   $('#passwordGroup').hide();
            }
        });

        $('#btn-create').on('click',function(){
            _this.create();
        });

        $('#btn-groupJoin').on('click',function(){
             _this.groupJoin();
        });

       $('#btn-passwordCheck').on('click',function(){
            _this.passwordCheck();
       });

       $('#groupThumbnail').on('change',function(){
            _this.preLoading(this);
       });

        $('#pageList .page-item a').on('click',function(e){
            _this.pageChange();
        });


    },
    create:function(){

        var form=$("#groupForm")[0];
        var formData=new FormData(form);
        formData.append("title",$('#title').val());
        formData.append("description",$('#description').val());
        if($('#password').val().length!=0){
            formData.append("password",$('#password').val());
        }

        formData.append("currentNum",0);
        formData.append("totalNum",$('#totalNum').val());
        formData.append("file",$("#groupThumbnail")[0].files[0]);


         $.ajax({
             type:'POST',
             url:'/api/group',
             enctype: 'multipart/form-data',
             processData:false,
             contentType:false,
             cache:false,
             data: formData
             }).done(function(){
                 alert('그룹이 생성됬습니다.');
                 window.location.href='/';
             }).fail(function(error){
                 alert(JSON.stringify(error));
             });
    },
    groupIn:function(groupId,isSecretRoom){
            selectGroupId=groupId;
          if(isSecretRoom==true){
                $('#passwordModal').modal('show');
          }else{
                window.location.href='/group/'+groupId;
          }

    },
    passwordCheck:function(){
        var data={
            id:selectGroupId*1,
            password:$('#groupPassword').val()
        }

        $.ajax({
             type:'POST',
             url:'/api/group/check/p',
             dataType:'json',
             contentType:'application/json; charset=utf-8',
             data: JSON.stringify(data),
             success:function(isRight){

                if(isRight){
                    window.location.href='/group/'+selectGroupId;
                }else{
                    alert('비밀번호가 틀렸습니다.');
                }
             }
        });
    },
    groupJoinModal:function(groupId){

        $('#groupJoinModal').modal('show');
        $('#groupJoinId').val(groupId);
    },
    groupJoin:function(){
         var data={
              groupId: $('#groupJoinId').val()
         }
         $.ajax({
              type:'POST',
              url:' /api/group/join',
              dataType:'json',
              contentType:'application/json; charset=utf-8',
              data: JSON.stringify(data)
         }).done(function(){
              alert('그룹 관리자가 승인하면 이용가능합니다.');
              window.location.href='/';
         }).fail(function(error){
              alert('error 다시 시도해주십시오 '+JSON.stringify(error));
         });
    },
    groupItemClick:function(groupItemId,isSecretRoom){
        //그룹 아이템 클릭 했을때
        //이 그룹의 참여자인가? 신규가입자인가?

        $.ajax({
            type:'get',
            url:'/api/group/memberCheck/'+groupItemId,
            dataType:'json',
            contentType:'application/json;charset=utf-8',
            success:function(isMember){
                if(isMember){
                     //비밀방인가 그냥 방인가?
                     if(isSecretRoom==true){
                        $('#passwordModal').modal('show');
                     }else{
                        window.location.href='/group/'+groupItemId;
                     }
                }else{
                      if(isSecretRoom==true){
                        $('#passwordModal').modal('show');
                      }else{
                        $('#groupJoinModal').modal('show');
                        $('#groupJoinId').val(groupItemId);
                      }

                }
            }
        })
    },
    preLoading:function(input){
          if(input.files&&input.files[0]){
            var reader=new FileReader();
            reader.onload=function(e){
                $('#preloaded_thumbnail').attr('src',e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
          }
    },
    pageChange:function(groupId,pageNum){

                    console.log('클릭');
                    postList=$('.postList');
                    //href='/group/"+groupId+"?"+"page="+value+"
                    $.ajax({
                        type:'GET',
                        url:"/api/v1/group/"+groupId+"?page="+pageNum,
                        dataType:'json',
                        contentType:'application/json; charset=utf-8'
                    }).done(function(response){
                        postList.children().remove();
                        var data=response.data;
                        var groupId=response.groupId;
                        var pageList=response.pageList;

                        var html="";
                        //onClick='reply_update_display("+reply.id+")'
                        //<a th:href="@{'/post/read/'+${group.id}+'/'+${post.id}}" th:text="${post.title}"></a>
                        data.forEach(function(value){
                             html+="<tr>"+
                               "<td style='width:10%'>"+value.id+"</td>"+
                               "<td style='width:60%'><a href='/post/read/"+groupId+"/"+value.id+"'>"+value.title+"</a></td>"+
                               "<td style='width:10%'>"+value.name+"</td>"+
                               "<td style='width:10%'>"+value.modifiedDate+"</td>"+
                               "<td style='width:10%'>"+value.clickCount+"</td>"+
                            "</tr>";

                         });
                         postList.prepend(html);
                         html="";
                         //onClick="chat.inviteToChatRoom(\''+value.name+'\')"
                         $('#pageList').children().remove();
                         pageList.forEach(function(value){
                            html+="<li class='page-item'>"+
                                      "<a class='page-link test' href='javascript:void(0);' onclick='group.pageChange(\""+groupId+"\",\""+value+"\");'>"+value+"</a>"+
                                  "</li>";
                         });

                         $('#pageList').prepend(html)

                    });
    }

};
group.init();