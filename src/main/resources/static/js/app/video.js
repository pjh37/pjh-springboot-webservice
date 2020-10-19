

var video={
    init:function(){
        var _this=this;
        $('#video-uploadSpinner').hide();
        $('#btn-video-upload').on('click',function(){
            _this.upload();
        });

    },
    upload:function(){
        var form=$("#videoForm")[0];
        var formData=new FormData(form);
        formData.append("groupId",$('#groupId').val());
        formData.append("title",$('#videoTitle').val());
        formData.append("file",$("#videoFile")[0].files[0]);
        $('#video-uploadSpinner').show();
        $.ajax({
             type:'POST',
             url:'/api/video',
             enctype: 'multipart/form-data',
             processData:false,
             contentType:false,
             cache:false,
             data: formData
             }).done(function(){
                 $('#video-uploadSpinner').hide();
                 alert('업로드 완료');
                 window.location.href='/';
             }).fail(function(error){
                 alert('업로드 실패 : '+JSON.stringify(error));
             });
    }
};
video.init();