require.config({
    baseUrl: 'cportalJS'
});

define(['common/ajaxUtil', 'common/utils'], function (ajaxUtil, utils) {

    let billingTab = {};


    billingTab.createView = function () {
        let template = new EJS({url: 'cportalJS/setting/ejs/billing.ejs'}).render();
        $('#billingTab').append(template);
    }



    return billingTab;
});
