
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
    },
    create:function(){
        var form=$("#groupForm")[0];
        var formData=new FormData(form);
        formData.append("title",$('#title').val());
        formData.append("description",$('#description').val());
        formData.append("password",$('#password').val());
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
    }
}
group.init();