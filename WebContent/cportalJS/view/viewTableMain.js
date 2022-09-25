require.config({
    baseUrl: '/cportalJS'
});

require(['common/ajaxUtil', 'common/utils', 'common/menuNav'], function (ajaxUtil, utils, menuNav) {


    function init() {
        $('#viewTable').addClass( 'active' );
        menuNav.setMenuNav('view');
        $('#contentBody').append(new EJS({url: '/cportalJS/view/ejs/viewMainView.ejs'}).render());
        if (feather) {
            feather.replace({width: 14, height: 14});
        }

        createTitle();

        $('#registerViewBtn').click(function () {
            registerModal();
        });
    }

    function registerModal() {

        $('#registerModal').empty();
        $('#registerModal').append(new EJS({url: '/cportalJS/view/ejs/viewTable/registerViewLinkModal.ejs'}).render());

        $('#linkModal').modal('show');

        $('#saveBtn').click(function () {

            let viewLink = {};
            viewLink.title = $('#title').val();
            viewLink.link = $('#link').val();
            viewLink.ownerId = window.id;
            viewLink.productType = $('input[name=productRadios]:checked').val();

            ajaxUtil.makeAjax("post", '/view/register/link/mail', JSON.stringify(viewLink), $('#linkModalBody')).done(function(msg){
                console.log(msg);
                $('#linkModal').modal('hide');
                Swal.fire({title: 'success', text: '등록 요청에 성공하였습니다', icon: 'success'});
            }).
            fail(function(xhr, textStatus){
                $('#linkModal').modal('hide');
                console.log(xhr);
                console.log(textStatus);
                Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
            });

        });
    }


    function createTitle() {
        ajaxUtil.makeAjax("get", '/view/title', null, $('#companyList')).done(function (msg) {

            if (!utils.isEmpty(msg.premier)) {
                makePremier(msg.premier, $('#premierDivId'));
            }

            if (!utils.isEmpty(msg.normal)) {
                makeNormal(msg.normal, $('#normalDivId'));
            }

        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });
    }

    function makePremier(premier, div) {
        premier.forEach((value) => {
            let template = new EJS({url: '/cportalJS/view/ejs/viewTable/premier.ejs'}).render(value);
            div.append(template);
        })
    }

    function makeNormal(normal, div) {
        normal.forEach((value) => {
            let template = new EJS({url: '/cportalJS/view/ejs/viewTable/normal.ejs'}).render(value);
            div.append(template);
        })
    }


    init();
});
