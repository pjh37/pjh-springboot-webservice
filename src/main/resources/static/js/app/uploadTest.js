$('#btn-save').on('click', uploadImage);

    function uploadImage() {
        var file = $('#img')[0].files[0];
        var formData = new FormData();
        formData.append('data', file);

        $.ajax({
            type: 'POST',
            url: '/test/upload',
            data: formData,
            processData: false,
            contentType: false
        }).done(function (data) {
            $('#result-image').attr("src", 'http://'+data);
        }).fail(function (error) {
            alert(error);
        })
    }