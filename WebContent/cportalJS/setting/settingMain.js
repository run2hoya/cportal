require.config({
    baseUrl: 'cportalJS'
});

require(['common/ajaxUtil',
    'setting/accountTab',
    'setting/billingTab',
    'setting/notiTab',
    'setting/securityTab'
    ],
    function (ajaxUtil, accountTab, billingTab, notiTab, securityTab) {

    let id = null;

    function init() {
        createMainTemplate();
        addEvent();

    }

    function createMainTemplate() {
        let template = new EJS({url: 'cportalJS/setting/ejs/settingMainView.ejs'}).render();
        $('#contentBody').append(template);

        accountTab.createView();
        billingTab.createView();
        notiTab.createView();
        securityTab.createView();

    }

    function addEvent() {

       if (feather) { feather.replace({width : 14, height: 14});}
    }


    init();
});
