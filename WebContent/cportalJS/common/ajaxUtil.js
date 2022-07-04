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
            }
        });
    }


    return ajaxUtil;
});
