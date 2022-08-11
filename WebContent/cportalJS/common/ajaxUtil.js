define([], function () {

    let ajaxUtil = {};

    ajaxUtil.get = function (url, blockUi) {
        return $.ajax({
            type       : "get",
            url        : url,
            dataType   : "json",
            contentType: "application/json; charset=utf-8",
            async      : true,
            beforeSend : function (xhr) {
                if (blockUi != null) {
                    blockUi.block({
                        message   : '<div class="spinner-border text-primary" role="status"></div>',
                        css       : {
                            backgroundColor: 'transparent',
                            border         : '0'
                        },
                        overlayCSS: {
                            backgroundColor: '#fff',
                            opacity        : 0.8
                        }
                    });
                }
            },
            complete   : function () {
                if (blockUi != null)
                    blockUi.unblock();
            }, error   : (jqXHR) => {
                if (jqXHR.status === 403) {
                    alert("[세션 없음] 로그인 해주세요");
                    location.replace("/login");
                }
            }
        });
    }

    ajaxUtil.makeAjax = function (type, url, content, blockUi) {
        return $.ajax({
            type       : type,
            url        : url,
            dataType   : "json",
            contentType: "application/json; charset=utf-8",
            async      : true,
            data       : content,
            beforeSend : function (xhr) {
                if (blockUi != null) {
                    blockUi.block({
                        message   : '<div class="spinner-border text-primary" role="status"></div>',
                        css       : {
                            backgroundColor: 'transparent',
                            border         : '0'
                        },
                        overlayCSS: {
                            backgroundColor: '#fff',
                            opacity        : 0.8
                        }
                    });
                }
            },
            complete   : function () {
                if (blockUi != null)
                    blockUi.unblock();
            }, error   : (jqXHR) => {
                if (jqXHR.status === 403) {
                    alert("[세션 없음] 로그인 해주세요");
                    location.replace("/login");
                }
            }
        });
    }


    return ajaxUtil;
});
