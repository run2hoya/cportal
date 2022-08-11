require.config({
    baseUrl: '/cportalJS'
});

define(['common/ajaxUtil', 'common/utils', 'common/board/board'], function (
    ajaxUtil, utils, board) {

    let boardMain = {};

    boardMain.createBoard = function () {
        $('#contentBody').append(new EJS({url: '/cportalJS/dododo/board/ejs/boardMain.ejs'}).render());
        if (feather) { feather.replace({width : 14, height: 14});}
        initBoard();
    }

    function initBoard() {
        board.createBoard($('#noticeBoard'), 'DO_NOTI', '게시판', 'modal-xl', true, 10);
    }

    return boardMain;
});
