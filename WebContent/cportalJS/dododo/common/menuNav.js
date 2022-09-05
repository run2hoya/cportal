require.config({
    baseUrl: '/cportalJS'
});

define(['dododo/board/boardMain', 'company114/company114Main'], function (
    boardMain, company114Main) {

    let menuNav = {};

    menuNav.setMenuNav = function () {
        $('#dododo_main').click(function () { menuNav.goPage('board');});
        $('#company114').click(function () {console.log('wqe'); menuNav.goPage('company114');});

    }

    menuNav.goPage = function (page) {

        $('#contentBody').empty();
        if(page === 'board') {boardMain.createBoard();}
        else if(page === 'company114') {company114Main.init();}

    }
    return menuNav;
});
