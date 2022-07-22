require.config({
    baseUrl: 'cportalJS'
});

define(['common/ajaxUtil', 'common/utils'], function (ajaxUtil, utils) {

    let billingTab = {};


    billingTab.createView = function () {
        let template = new EJS({url: 'cportalJS/setting/ejs/security.ejs'}).render();
        $('#securityTab').append(template);

        setEvents();
    }

    function setEvents() {
        var form = $('#security-form');

        if (form.length) {
            form.validate({
                rules   : {
                    oldPassword  : {
                        required: true
                    },
                    newPassword  : {
                        required: true,

                    },
                    reNewPassword: {
                        required: true,
                        equalTo : '#newPassword'
                    }
                },
                messages: {
                    oldPassword  : {
                        required: '현재 패스워드를 입력해 주세요'
                    },
                    newPassword  : {
                        required: '새로운 패스워드를 입력해 주세요'
                    },
                    reNewPassword: {
                        required: '새로운 패스워드를 입력해 주세요',
                        equalTo : '비밀번호가 다릅니다. 다시 확인해 주세요'
                    }
                },
                submitHandler: function () {
                    let user = {};
                    user.password = $('#oldPassword').val();
                    user.newPassword = $('#newPassword').val();
                    ajaxUtil.makeAjax("put", './user/password/' + window.userId, JSON.stringify(user), $('#security-form')).done(function (msg) {
                        console.log(msg);
                        if(msg.resultCode === 200) {
                            Swal.fire({title: 'success', text: '저장에 성공하였습니다', icon: 'success'});
                        } else
                            Swal.fire({ icon: 'error',  title: '저장 실패', html: 'Code : ' + msg.resultCode + "</br>" + msg.description});

                        return true;

                    }).fail(function (xhr, textStatus) {
                        console.log(xhr);
                        console.log(textStatus);
                        Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
                        return false;
                    });

                }
            });
        }

    }

    return billingTab;
});
