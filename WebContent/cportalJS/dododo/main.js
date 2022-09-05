require.config({
    baseUrl: '/cportalJS'
});

require(['dododo/common/menuNav'], function (
    menuNav) {


    function init() {
        menuNav.setMenuNav();
        menuNav.goPage(window.page);
        $('#' + window.page).addClass( 'active' );
    }


    init();
});
