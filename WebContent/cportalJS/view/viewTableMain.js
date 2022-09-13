require.config({
    baseUrl: '/cportalJS'
});

require(['common/ajaxUtil', 'common/utils'], function (ajaxUtil, utils) {


    function init() {
        $('#contentBody').append(new EJS({url: '/cportalJS/view/ejs/viewMainView.ejs'}).render());
        if (feather) {
            feather.replace({width: 14, height: 14});
        }

        createTitle();
    }

    function createTitle() {
        ajaxUtil.makeAjax("get", '/view/title', null, $('#companyList')).done(function (msg) {

            if (!utils.isEmpty(msg.premier)) {
                $('#premierCompanyCnt').text(msg.premier.length);
                makePremier(msg.premier, $('#premierDivId'));
            }

        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });
    }

    function makePremier(premier, div) {

        premier.forEach((value) => {
            let template = new EJS({url: '/cportalJS/view/ejs/premier.ejs'}).render(value);
            div.append(template);
        })
    }


    init();
});
