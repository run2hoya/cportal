require.config({
    baseUrl: '/cportalJS'
});

require(['dododo/common/menuNav'], function (
    menuNav) {


    function init() {
        menuNav.goPage(window.page);
    }


    init();
});
