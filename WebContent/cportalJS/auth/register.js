(function ($) {


    $("#register").click(function () {

        let data = checkInfo()
        if (data == null) {
            return false;
        }

        $.ajax({
            type       : "post",
            url        : "./user",
            dataType   : "json",
            contentType: "application/json; charset=utf-8",
            async      : false,
            data       : JSON.stringify(data),
            success    : function (response) {
                console.log(response);
                if (response.resultCode === 200) {
                    Swal.fire({
                        title            : "가입에 성공하였습니다",
                        html             : "로그인 해주세요.",
                        icon             : 'success',
                        allowOutsideClick: false
                    }).then(function (result) {
                        window.location.href = "/";
                    });
                } else {
                    Swal.fire({icon: 'error', title: '가입 실패', html: 'Code : ' + response.resultCode + "</br>" + response.description});
                }

            },
            error      : function (xhr, textStatus, error) {
                console.log(xhr);
                console.log(textStatus);
                console.log(error);
                Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
            }
        });

        return false;

    });

    // 특수 문자 체크
    function checkSpecial(str) {
        var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;

        if (special_pattern.test(str) == true) {
            return true;
        } else {
            return false;
        }
    }

    function checkInfo() {

        var getMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);
        var getCheck = RegExp(/^[a-zA-Z0-9]{3,12}$/);

        // 아이디 공백 확인
        if ($("#userId").val() == "") {
            Swal.fire({title: 'ERROR', text: 'ID를 입력해주세요', icon: 'error'});
            return null;
        }

        if (!getCheck.test($("#userId").val())) {
            Swal.fire({title: 'ERROR', text: 'ID는 3자 이상 영문 대소문자와 숫자로만 입력 가능합니다.', icon: 'error'});
            return null;
        }

        // 비밀번호
        if ($("#password").val() == "") {
            Swal.fire({title: 'ERROR', text: '비밀번호를 입력해주세요', icon: 'error'});
            return null;
        }

        // 아이디랑 비밀번호랑 같은지
        if ($("#id").val() == ($("#password").val())) {
            Swal.fire({title: 'ERROR', text: '비밀번호와 ID가 같으면 안됩니다', icon: 'error'});
            return null;
        }

        // 비밀번호 똑같은지
        if ($("#password").val() != ($("#re_password").val())) {
            Swal.fire({title: 'ERROR', text: '비밀번호가 다릅니다. 다시 확인해 주세요', icon: 'error'});
            return null;
        }

        if ($("#nickName").val() == "") {
            Swal.fire({title: 'ERROR', text: '닉네임을 입력해주세요', icon: 'error'});
            return null;
        }

        if (checkSpecial($("#nickName").val()) === true) {
            Swal.fire({title: 'ERROR', text: '닉네임에 특수문자를 넣을수 없습니다.', icon: 'error'});
            return null;
        }


        // 이메일 공백 확인
        if ($("#email").val() == "") {
            Swal.fire({title: 'ERROR', text: 'e-mail을 입력해주세요', icon: 'error'});
            return null;
        }

        // 이메일 유효성 검사
        if (!getMail.test($("#email").val())) {
            Swal.fire({title: 'ERROR', text: 'e-mail 형식에 맞게 입력해주세요', icon: 'error'});
            return null;
        }

        let userAuth;
        if ($("input[name='userType']:checked").val() === 'EXTERNAL')
            userAuth = 'ROLE_GUEST';
        else
            userAuth = 'ROLE_CASTIS';

        var data = {
            "userId"   : $('#userId').val().trim(),
            "registerName"   : $('#registerName').val().trim(),
            "password" : $('#password').val().trim(),
            "authority": userAuth,
            "enabled"  : true,
            "nickName" : $('#nickName').val().trim(),
            "email"    : $('#email').val().trim(),
            "userType" : $("input[name='userType']:checked").val()
        };
        return data;
    }

})(jQuery);