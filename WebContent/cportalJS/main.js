require.config({
    baseUrl: '/cportalJS'
});

require(['common/menuNav'], function (
    menuNav) {


    function init() {
        menuNav.setMenuNav('cportal');
        menuNav.goPage('cportal', window.page);
    }


    init();
});
