define(['common/utils', 'common/ajaxUtil'], function (utils, ajaxUtil) {

    let summerNote = {};
    let productType, dir;

    summerNote.init = function (obj, productType, dir) {
        let option;
        console.log(productType);
        if (productType === 'PREMIER' || productType === 'PLATINUM') {
            option = ['insert', ['picture', 'link', 'hr']];
        } else {
            option = ['insert', ['link', 'hr']]
        }
        summerNote.productType = productType;
        summerNote.dir = dir;

        let config = {
            toolbar  : [
                // [groupName, [list of button]]
                ['fontname', ['fontname']],
                ['fontsize', ['fontsize']],
                ['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
                ['color', ['forecolor', 'color']],
                ['table', ['table']],
                ['para', ['ul', 'ol', 'paragraph']],
                ['height', ['height']],
                option
            ],
            fontNames: ['Arial', 'Arial Black', 'NotoSansKR', 'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋움체', '바탕체', 'sc'],
            fontSizes: ['8', '9', '10', '11', '12', '14', '16', '18', '20', '22', '24', '28', '30', '36', '50', '72'],
            lang     : "ko-KR",
            height   : 500,
            focus    : true,
            callbacks: {
                onImageUpload: function (files, editor, welEditable) {
                    for (let i = files.length - 1; i >= 0; i--) {
                        sendFile(files[i], this);
                    }
                },
                onMediaDelete: function (target) {
                    deleteFile(target[0].src);
                }
            }
        };

        obj.summernote(config);
        obj.summernote('fontSize', 16);
        obj.summernote('fontName', 'NotoSansKR');

        return obj;

    }

    function deleteFile(file) {

        console.log(file);
        ajaxUtil.makeAjax('delete', '/upload/file?fileName=' + file, null, null).done(function (msg) {
            console.log(msg);
        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
        });
    }

    function sendFile(file, el) {

        if (summerNote.productType === 'PREMIER' || summerNote.productType === 'PLATINUM') {
            let form_data = new FormData();
            form_data.append('file', file);
            $.ajax({
                data       : form_data,
                type       : "POST",
                dataType   : 'text',
                url        : '/upload/file?dir=' + summerNote.dir,
                cache      : false,
                contentType: false,
                enctype    : 'multipart/form-data',
                processData: false,
                success    : function (img_name) {
                    $(el).summernote('editor.insertImage', img_name);
                },
                error      : function (xhr, textStatus, error) {
                    console.log(xhr);
                    console.log(textStatus);
                    console.log(error);
                    Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
                    return false;
                }
            });
        } else {
            Swal.fire({title: 'ERROR', text: '해당 상품은 이미지를 이용 할 수 없습니다.', icon: 'error'});
            return;
        }


    }





    return summerNote;
});
