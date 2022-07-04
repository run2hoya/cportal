(function($) {



	function userData() {
		var userId, password, authority, enabled, name, email;		
	};
	
	$("#submit").click(function() {
		var data = checkInfo()

		var userData = JSON.stringify(data);
		

		$.ajax({
			type : "post",
			url : 'user',
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			data : userData,
			async : true,
			success : function(response) {
				console.log(response);
				if(response.resultCode == 200) {
					swal({			
		                title: "가입에 성공하였습니다",
		                html: "로그인 해주세요.",
		                type: "success",	                            
		                allowOutsideClick: false
		            }).then(function (result) { 
		            	window.location.href = "/cportal";
		            }); 	
				} else {					
					swal({ allowOutsideClick: true, type: 'error', title: '가입 실패', html: 'Code : ' + response.resultCode + "</br>" + response.description});
				}
	    		
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
				return false;
			}
		});
	});

	function checkInfo() {
		var getIntro = $("#introduction").val().replace(/\s|/gi, '');		
		var getMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);
		var getCheck = RegExp(/^[a-zA-Z0-9]{4,12}$/);
		var getName = RegExp(/^[가-힣]+$/);
		var fmt = RegExp(/^\d{6}[1234]\d{6}$/); // 형식 설정		

		// 아이디 공백 확인
		if ($("#id").val() == "") {
			swal({ allowOutsideClick: true, type: 'error', title: 'ERROR', html: 'ID를 입력해주세요'});			
			return null;
		}
		
		if (!getCheck.test($("#id").val())) {
			swal({ allowOutsideClick: true, type: 'error', title: 'ERROR', html: 'ID는 4자 이상 영문 대소문자와 숫자로만 입력 가능합니다.'});
			return null;
		}
		
		// 비밀번호
		if ($("#password").val() == "") {
			swal({ allowOutsideClick: true, type: 'error', title: 'ERROR', html: '비밀번호를 입력해주세요'});			
			return null;
		}

		// 아이디랑 비밀번호랑 같은지
		if ($("#id").val() == ($("#password").val())) {
			swal({ allowOutsideClick: true, type: 'error', title: 'ERROR', html: '비밀번호와 ID가 같으면 안됩니다'});			
			return null;
		}

		// 비밀번호 똑같은지
		if ($("#password").val() != ($("#re_password").val())) {
			swal({ allowOutsideClick: true, type: 'error', title: 'ERROR', html: '비밀번호가 다릅니다. 다시 확인해 주세요 '});			
			return null;
		}
		
		if ($("#name").val() == "") {
			swal({ allowOutsideClick: true, type: 'error', title: 'ERROR', html: '이름을 입력해주세요'});			
			return null;
		}
		


		// 이메일 공백 확인
		if ($("#email").val() == "") {
			swal({ allowOutsideClick: true, type: 'error', title: 'ERROR', html: 'e-mail을 입력해주세요'});
			return null;
		}

		// 이메일 유효성 검사
		if (!getMail.test($("#email").val())) {
			swal({ allowOutsideClick: true, type: 'error', title: 'ERROR', html: 'e-mail 형식에 맞게 입력해주세요'});
			return null;
		}
		
	
		var data = { 
				"userId" : $('#id').val().trim(),
				"password" : $('#password').val().trim(),
				"authority" : 'ROLE_GUEST',
				"enabled" : true,
				"name" : $('#name').val().trim(),
				"email" : $('#email').val().trim()
		};
		return data;
	}

})(jQuery);