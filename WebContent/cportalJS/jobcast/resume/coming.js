require.config({
    baseUrl: '/cportalJS'
});

define(['common/ajaxUtil', 'common/utils', 'common/imageSend', 'common/payment/payment'],
    function (ajaxUtil, utils, imageSend, payment) {

        let coming = {};

        coming.init = function () {

            $('#contentBody').append(new EJS({url: '/cportalJS/jobcast/resume/ejs/coming.ejs'}).render(
                {}
            ));
        }



        return coming;
    });
