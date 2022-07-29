require.config({
    baseUrl: 'cportalJS'
});

define(['common/ajaxUtil', 'common/utils', 'common/imageSend'], function (ajaxUtil, utils, imageSend) {

    let accountTab = {};


    accountTab.createView = function () {
        let template = new EJS({url: 'cportalJS/setting/ejs/account.ejs'}).render();
        $('#accountTab').append(template);

        setAccount();
        setEvents();
    }


    function setAccount() {

        ajaxUtil.get('./user/' + window.userId, $("#account-details")).done(function (msg) {
            $('#account-upload-img').attr('src', msg.userImg);
            $('#nickName').val(msg.nickName);
            $('#accountEmail').val(msg.email);
            $('#introduction').val(msg.introduction);
            $('#phone').val(msg.phone);
            imageSend.returnImgName = msg.userImg;

        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });
    }

    function setEvents() {
        imageSend.init(400, 400, window.userId, 'userImg', null);
        let form = $('#account-form');
        if (form.length) {
            form.validate({
                rules        : {
                    nickName    : {
                        required: true,
                    },
                    accountEmail: {
                        required: true,
                        email   : true
                    },
                    introduction: {
                        required : false,
                        maxlength: 40
                    }
                },
                messages     : {
                    accountEmail: {
                        required: '이메일을 입력해 주세요'
                    },
                    introduction: {
                        maxlength: "최대 {0}글자이하이어야 합니다"
                    }
                },
                submitHandler: function () {
                    let user = {};
                    user.userImg = imageSend.returnImgName;
                    user.nickName = $('#nickName').val();
                    user.email = $('#accountEmail').val();
                    user.introduction = $('#introduction').val();
                    user.phone = $('#phone').val();

                    //사업부 등록 요청
                    ajaxUtil.makeAjax("put", './user/' + window.userId, JSON.stringify(user), $('#account-details')).done(function (msg) {
                        console.log(msg);
                        Swal.fire({title: 'success', text: '저장에 성공하였습니다', icon: 'success'});
                    }).fail(function (xhr, textStatus) {
                        console.log(xhr);
                        console.log(textStatus);
                        Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
                    });
                }
            });
        }
    }

    return accountTab;
});
