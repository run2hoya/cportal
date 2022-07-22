require.config({
    baseUrl: 'cportalJS'
});

define(['common/ajaxUtil', 'common/utils'], function (ajaxUtil, utils) {

    let notiTab = {};


    notiTab.createView = function () {
        let template = new EJS({url: 'cportalJS/setting/ejs/noti.ejs'}).render();
        $('#notiTab').append(template);

        setNoti();
    }

    function setNoti() {

        ajaxUtil.get('./user/setting/' + window.userId, $("#noti-details")).done(function (msg) {

            $("input:checkbox[id='adNotiEmail']").prop("checked", msg.adNotiEmail);
            $("input:checkbox[id='adNotiApp']").prop("checked", msg.adNotiApp);
            $("input:checkbox[id='jobcastNotiEmail']").prop("checked", msg.jobcastNotiEmail);
            $("input:checkbox[id='jobcastNotiApp']").prop("checked", msg.jobcastNotiApp);

        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });
    }


    return notiTab;
});
