require.config({
    baseUrl: '/cportalJS/dododo'
});

define(['board/boardMain'], function (
    boardMain) {

    let menuNav = {};

    menuNav.setMenuNav = function () {
        $('#dododo_main').click(function () { menuNav.goPage('board');});

    }

    menuNav.goPage = function (page) {

        $('#contentBody').empty();
        if(page === 'board') {boardMain.createBoard();}

    }
    return menuNav;
});
