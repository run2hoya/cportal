require.config({
    baseUrl: '/cportalJS',
});

require(['common/ajaxUtil', 'common/utils', 'common/summerNote'], function (ajaxUtil, utils, summerNote) {

    function openPopup() {

        ajaxUtil.get('/company/info/' + window.targetId, $('#contentBody')).done(function (msg) {
            console.log(msg);
            if (!utils.isEmpty(msg)) {
                let template = new EJS({url: '/cportalJS/company114/ejs/companyInfoModal.ejs'}).render(
                    msg, {companyId : window.targetId});
                $('#contentBody').append(template);

                if (!utils.isEmpty(msg.content)) {
                    $('#editor').summernote('code', msg.content);
                    $('#editor').summernote('destroy');
                }

                $('#saveBtn').hide();
                $('#editBtn').hide();

                var clipboard = new ClipboardJS('#shareBtn', {container: document.getElementById('exampleModalScrollable')});
                clipboard.on( 'success', function() {Swal.fire({title: 'SUCCESS', text: '주소가 복사되었습니다.', icon: 'success'});} );
                clipboard.on( 'error', function() {Swal.fire({title: 'ERROR', text: '해당 브라우저는 복사 기능이 지원되지 않습니다', icon: 'error'});} );


                $('#exampleModalScrollable').modal('show');
            }
        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });
    }


    openPopup();
});
