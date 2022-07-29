require.config({
    baseUrl: '/cportalJS'
});

define(['common/ajaxUtil', 'common/utils'], function (
    ajaxUtil, utils) {

    let caportalMain = {};

    caportalMain.init = function (wantedType) {
        $('#contentBody').append(new EJS({url: '/cportalJS/cportal/ejs/cportalMain.ejs'}).render(
        ));
        if (feather) { feather.replace({width : 14, height: 14});}
    }



    return caportalMain;
});
