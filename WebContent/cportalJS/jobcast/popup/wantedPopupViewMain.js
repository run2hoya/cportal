require.config({
    baseUrl: '/cportalJS'
});

require(['common/ajaxUtil', 'common/summerNote', 'common/utils',],
    function (ajaxUtil, summerNote, utils,) {


        function init() {
            loadWantedInfo();
        }


        function loadWantedInfo() {
            let url = '/wanted/' + window.wantedId;
            ajaxUtil.makeAjax("get", url, null, $('.content-overlay')).done(function (msg) {

                $('#contentBody').append(new EJS({url: '/cportalJS/jobcast/popup/ejs/wantedPopupViewMain.ejs'}).render(msg));
                $('#remainDays').text((moment(msg.endDate).diff(moment(), 'days') + 1) + '일');
                $('#email').val(msg.email)

                if (utils.isEmpty(msg.content) === false) {
                    $('#editor').summernote('code', msg.content);
                    $('#editor').summernote('destroy');
                }

                $('#modal').append(new EJS({url: '/cportalJS/jobcast/popup/ejs/applicantModal.ejs'}).render());
                addEvent();

                if (feather) {
                    feather.replace({width: 14, height: 14});
                }

            }).fail(function (xhr, textStatus) {
                console.log(xhr);
                console.log(textStatus);
                Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
            });
        }

        function addEvent() {

            let applicantModal = new bootstrap.Modal($('#applicant'));
            $('#wantedBtn').click(function () {
                applicantModal.show();
            });

            $('#submit').click(function () {
                let applicant = {};
                applicant.name = $('#name').val();
                applicant.email = $('#apllicantEmail').val();
                applicant.phone = $('#apllicantPhone').val();
                applicant.companyEmail = $('#email').val();
                applicant.wantedTitle = $('#title').text();
                applicant.wantedId = window.wantedId;


                //사업부 등록 요청
                ajaxUtil.makeAjax("post", '/wanted/applicant', JSON.stringify(applicant), $('#applicant')).done(function(msg){
                    console.log(msg);
                    applicantModal.hide();
                    Swal.fire({title: 'success', text: '등록 요청에 성공하였습니다', icon: 'success'});
                }).
                fail(function(xhr, textStatus){
                    applicantModal.hide();
                    console.log(xhr);
                    console.log(textStatus);
                    Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
                });
            });

        }


        init();
    });
