define(['common/utils'], function (utils) {

    let imageSend = {};
    let returnImgName;

    imageSend.init = function (maxWidth, maxHeight, fileName, urlPath) {

        let accountUploadImg = $('#account-upload-img');
        let accountUserImage = $('.uploadedAvatar');
        let accountResetBtn = $('#account-reset');

        if (accountUserImage) {
            var resetImage = accountUserImage.attr('src');
            let imgupload = document.getElementById('account-upload');
            imgupload.addEventListener('change', function (e) {
                if (e.target.files) {
                    let imageVal = e.target.files[0];

                    var reader = new FileReader();
                    reader.onload = function (e) {


                        var img = document.createElement("img");
                        img.onload = function (event) {

                            var canvas = document.createElement("canvas");
                            var ctx = canvas.getContext("2d");

                            ctx.drawImage(img, 0, 0);

                            var width = img.width;
                            var height = img.height;

                            if (width > height) {
                                if (width > maxWidth) {
                                    height *= maxWidth / width;
                                    width = maxWidth;
                                }
                            } else {
                                if (height > maxHeight) {
                                    width *= maxHeight / height;
                                    height = maxHeight;
                                }
                            }
                            canvas.width = width;
                            canvas.height = height;

                            // canvas에 변경된 크기의 이미지를 다시 그려줍니다.
                            var ctx = canvas.getContext("2d");
                            ctx.drawImage(img, 0, 0, width, height);
                            var dataurl = canvas.toDataURL("image/png");
                            accountUploadImg.attr('src', dataurl);

                            if(utils.isEmpty(fileName))
                                fileName = Date.now();
                            sendFile(urlPath, dataURItoBlob(dataurl), fileName , "png");
                        }
                        img.src = e.target.result;
                    }
                    reader.readAsDataURL(imageVal);
                }
            });

            accountResetBtn.on('click', function () {
                accountUserImage.attr('src', resetImage);
            });
        }

    }


    function dataURItoBlob(dataURI) {

        var byteString = atob(dataURI.split(',')[1]);

        // separate out the mime component
        var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

        var ab = new ArrayBuffer(byteString.length);
        var ia = new Uint8Array(ab);
        for (var i = 0; i < byteString.length; i++) {
            ia[i] = byteString.charCodeAt(i);
        }
        return new Blob([ab], {type: mimeString});
    }

    function sendFile(urlPath, file, fileName, ext) {

        let form_data = new FormData();
        form_data.append('file', file);
        $.ajax({
            data       : form_data,
            type       : "POST",
            dataType   : 'text',
            url        : urlPath + fileName + "/" + ext,
            cache      : false,
            contentType: false,
            enctype    : 'multipart/form-data',
            processData: false,
            success    : function (msg) {
                console.log(msg);
                imageSend.returnImgName = msg;
            },
            error      : function (xhr, textStatus) {
                console.log(xhr);
                console.log(textStatus);
                Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
                return false;
            }
        });
    }


    return imageSend;
});
