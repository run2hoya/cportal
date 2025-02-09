require.config({
    baseUrl: 'cportalJS'
});

define(['common/ajaxUtil', 'company114/makeCompanyView'], function (ajaxUtil, makeCompanyView) {

    let id = null;
    let company114Main = {};

    company114Main.init = function () {
        createMainTemplate();
        addEvent();
        makeCompanyView.createCompanyView();
    }

    function createMainTemplate() {
        let template = new EJS({url: 'cportalJS/company114/ejs/company114MainView.ejs'}).render();
        $('#contentBody').append(template);

        template = new EJS({url: 'cportalJS/company114/ejs/registerModal.ejs'}).render();
        $('#registerModal').append(template);

        template = new EJS({url: 'cportalJS/company114/ejs/simpleRegisterModal.ejs'}).render();
        $('#simpleRegisterModal').append(template);

        $('#twoFactorAuthAppsModal').on('shown.bs.modal', function () {
            $('.select2InModal').select2({
                placeholder: 'Select a type'
            });
        });
        $(document).ready(function() {
            $("#companyTooltip").tooltip();
        });


    }

    function addEvent() {
        let twoFactorAuthModal = new bootstrap.Modal($('#twoFactorAuthModal')),
            authAppsModal = new bootstrap.Modal($('#twoFactorAuthAppsModal'));

        $('#nextStepAuth').click(function () {
            var currentSelectMethod = document.querySelector('input[name=twoFactorAuthRadio]:checked').value;

            twoFactorAuthModal.hide();
            authAppsModal.show();

            if (currentSelectMethod === 'apps-auth') {
                $('#homeAddressRadio').prop('checked', true);
                $('#officeAddressRadio').prop('checked', false);
            } else {
                $('#officeAddressRadio').prop('checked', true);
                $('#homeAddressRadio').prop('checked', false);
            }
        });

        let simpleModal = new bootstrap.Modal($('#simple'));

        if(22 !== parseInt(window.id)){
            $('#etcPlus').hide();
        } else {
            $('#etcPlus').click(function () {
                simpleModal.show();
            });

            $('#simpleSubmit').click(function () {
                let companyInfoDTO = {};
                companyInfoDTO.companyName = $('#name').val();
                companyInfoDTO.companyCeo = $('#ceo').val();
                companyInfoDTO.companyEmail = $('#email').val();
                companyInfoDTO.phone = $('#phone').val();
                companyInfoDTO.companyDes = $('#simpleDes').val();
                companyInfoDTO.productType = 'ETC';

                ajaxUtil.makeAjax("post", '/company/new', JSON.stringify(companyInfoDTO), $('#simple')).done(function(msg){
                    simpleModal.hide();
                    Swal.fire({title: 'success', text: '등록 요청에 성공하였습니다', icon: 'success'});
                }).
                fail(function(xhr, textStatus){
                    simpleModal.hide();
                    console.log(xhr);
                    console.log(textStatus);
                    Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
                });
            });
        }


        let addNewAddressForm = $('#addNewAddressForm');
        if (addNewAddressForm.length) {
            addNewAddressForm.validate({
                rules        : {
                    companyName  : {
                        required: true
                    },
                    companyNameEn: {
                        required: true
                    },
                    companyCeo   : {
                        required: true
                    },
                    companyEmail : {
                        required: true,
                        email   : true
                    },
                    companyDes   : {
                        required : true,
                        maxlength: 40
                    }
                },
                messages     : {
                    companyDes: {
                        required : "사업부 한줄 소개를 입력해 주세요",
                        maxlength: "최대 {0}글자이하이어야 합니다"
                    }
                },
                submitHandler: function () {
                    let companyInfo = {};
                    companyInfo.companyName = $('#companyName').val();
                    companyInfo.companyNameEn = $('#companyNameEn').val();
                    companyInfo.companyCeo = $('#companyCeo').val();
                    companyInfo.companyEmail = $('#companyEmail').val();
                    companyInfo.companyDes = $('#companyDes').val();
                    companyInfo.companyView = $('#companyView').val();
                    companyInfo.productType = $('input[name=companyType]:checked').val();
                    companyInfo.companyNew = false;
                    companyInfo.companyPlace = $('#companyPlace').val();
                    companyInfo.companyType = $("#companyType option:selected").val();
                    companyInfo.registerId = window.id;

                    //사업부 등록 요청
                    ajaxUtil.makeAjax("post", './company/mail', JSON.stringify(companyInfo), $('#addNewAddressForm')).done(function(msg){
                        console.log(msg);
                        authAppsModal.hide();
                        Swal.fire({title: 'success', text: '등록 요청에 성공하였습니다', icon: 'success'});
                    }).
                    fail(function(xhr, textStatus){
                        authAppsModal.hide();
                        console.log(xhr);
                        console.log(textStatus);
                        Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
                    });

                }
            });
        }

       if (feather) { feather.replace({width : 14, height: 14});}
    }

    return company114Main;
});
