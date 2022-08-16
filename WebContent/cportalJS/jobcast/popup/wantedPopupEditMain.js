require.config({
    baseUrl: '/cportalJS'
});

require(['common/ajaxUtil', 'common/summerNote', 'common/utils', 'common/imageSend'],
    function (ajaxUtil, summerNote, utils, imageSend) {


    function init() {
        loadWantedInfo();
    }


    function updateContent() {
        let wantedWithContentDto = {};
        wantedWithContentDto.jobType = $("#select2-basic option:selected").val();
        wantedWithContentDto.bgImg = imageSend.returnImgName;
        ($('input[name="openRadio"]:checked').val() === "true")? wantedWithContentDto.open = true : wantedWithContentDto.open = false;
        wantedWithContentDto.content = $('#editor').summernote('code');
        wantedWithContentDto.email = $('#email').val();

        if(utils.isEmpty($('#email').val()) === true) {
            Swal.fire({title: 'WARNING', text: 'e-mail은 필수적으로 입력해주셔야 합니다.', icon: 'warning'});
            return;
        }


        ajaxUtil.makeAjax("put", '/wanted/' + window.targetId, JSON.stringify(wantedWithContentDto), $('#editor')).done(function (msg) {
            console.log(msg);
            Swal.fire({title: 'success', text: '저장에 성공하였습니다', icon: 'success'});
        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });
    }

    function loadWantedInfo() {
        let url = '/wanted/info/' + window.targetId ;
        ajaxUtil.makeAjax("get", url, null, $('.content-overlay')).done(function (msg) {
            console.log(msg);
            if(msg.registerId !== parseInt(window.id)) {
                Swal.fire({title: 'Warning', text: '수정 권한이 없습니다.', icon: 'warning'});
                return;
            }
            $('#contentBody').append(new EJS({url: '/cportalJS/jobcast/popup/ejs/wnatedPopupEditMain.ejs'}).render(msg));

            if (msg.open === true) {
                $('#open').prop('checked', true);
            } else
                $('#close').prop('checked', true);

            $('#remainDays').text((moment(msg.endDate).diff(moment(), 'days') +1 ) + '일');
            $("#select2-basic").val(msg.jobType).prop("selected", true);
            $('#email').val(msg.email);
            imageSend.returnImgName = msg.bgImg;
            imageSend.init(1200, 800, null, 'wanted', $("#wantedBg"));

            if(msg.productType !== 'PREMIER')
                $('#imageUpload').hide();

            summerNote.init($('#editor'), msg.productType, 'wanted');
            if(utils.isEmpty(msg.content) === false)
                $('#editor').summernote('code', msg.content);

            $("#wantedSaveBtn").on("click", function (e) {
                updateContent();
            });

            if (feather) { feather.replace({width : 14, height: 14});}

        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });
    }



    init();
});

