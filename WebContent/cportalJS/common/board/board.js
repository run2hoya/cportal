require.config({
    baseUrl: 'cportalJS'
});
define(['common/ajaxUtil', 'common/summerNote', 'common/utils'], function (ajaxUtil, summerNote, utils) {

    let board = {};
    let dt_ajax;
    let modalSize, boardType;

    board.createBoard = function (element, boardType, boardTitle, modalSize, bSearch, pageLength) {
        let template = new EJS({url: 'cportalJS/common/board/ejs/board.ejs'}).render(
            { boardTitle : boardTitle}
        );
        element.append(template);
        board.modalSize = modalSize;
        board.boardType = boardType;


        createWrite();
        create(boardType, bSearch, pageLength);
    }

    function create(boardType, bSearch, pageLength) {
        let start = moment().subtract(1, 'years');
        let end = moment();

        $('#daterange').daterangepicker({
            locale: {
                format: "YYYY년 MM월 DD일",
                separator: " ~ ",
                applyLabel: "확인",
                cancelLabel: "취소",
                daysOfWeek: ["일", "월", "화", "수", "목", "금", "토"],
                monthNames: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
            },
            maxSpan: {
                "years": 3
            },
            startDate: start,
            endDate: end,
            minYear: 2020,
            maxYear: 2040,
            showDropdowns: true,
            linkedCalendars: false
        }, function(start, end) {
            $('#daterange').val(start.format("YYYY년 MM월 DD일") + " ~ " + end.format("YYYY년 MM월 DD일"));
            dt_ajax.ajax.url('/board/list?startDate=' + start.format("YYYY-MM-DD") +
                '&endDate=' + end.format("YYYY-MM-DD") + '&boardType=' + boardType).load();
        });

        let config = {
            processing: true,
            order     : [[3, "desc"]],
            dom       : '<"d-flex justify-content-between align-items-center mx-0 row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>t<"d-flex justify-content-between mx-0 row"<"col-sm-12 col-md-6"i><"col-sm-12 col-md-6"p>>',
            language  : {
                paginate: {
                    // remove previous & next text from pagination
                    previous: '&nbsp;',
                    next    : '&nbsp;'
                }
            },
            ajax      : {
                url        : '/board/list?startDate=' + start.format("YYYY-MM-DD") + '&endDate=' + end.format("YYYY-MM-DD") + '&boardType=' + boardType,
                dataSrc    : function (data) {
                    console.log(data);
                    return data;
                },
                type       : "get",
                contentType: "application/json; charset=UTF-8",
                error      : function (xhr, textStatus, error) {
                    console.log(xhr);
                    console.log(textStatus);
                    console.log(error);
                    Swal.fire({title: 'ERROR', text: '정상적인 접근이 아닙니다.', icon: 'error'});
                }
            },
            columns   : [
                {data: null, "width": "50%"},
                {data: null},
                {data: "viewCnt"},
                {data: "registerDate"},

            ],
            columnDefs: [
                {
                    targets: 0, render: function (data, type, row) {
                        return '<a href="#" onclick="openBoardContent(' + data.id + ')">' + data.title + '</a>';
                    }
                },
                {
                    targets: 1, render: function (data, type, row) {
                        return '<div class="d-flex justify-content-left align-items-center"><div class="avatar pull-up my-0"><img src="' + data.userImg + '" alt="Avatar" height="26" width="26" />'
                            + '</div><p class="card-text mb-0 ms-25"> ' + data.nickName  + '</p></div>';
                    }
                }
            ]
        };

        config.pageLength = pageLength;
        if(bSearch === false) {
            config.searching = false;
            config.lengthChange = false;
        } else {
            config.searching = true;
            config.lengthChange = true;
        }


        let boardTable = $('.datatables-ajax');
        if (boardTable.length) {
            dt_ajax = boardTable.DataTable(config);
        }

        $('.dataTables_filter .form-control').removeClass('form-control-sm');
        $('.dataTables_length .form-select').removeClass('form-select-sm').removeClass('form-control-sm');
    }

    function createWrite() {
        //TODO 권한 관리 개발

        ajaxUtil.get('/authority/user/' + window.id + '?type=' + board.boardType, null).done(function (msg) {
            if (msg === false) {
                $('#writeBtn').remove();
            } else {
                $('#writeBtn').click(function () {
                    $('#boardModal').empty();
                    let template = new EJS({url: 'cportalJS/common/board/ejs/boardContentModal.ejs'}).render(
                        {modalSize : board.modalSize,
                            updateDate : '-'});
                    $('#boardModal').append(template);
                    addEvent(null);
                    summerNote.init($('#editor'), 'PREMIER', 'notice');
                    $('#modalScrollable').modal('show');
                });
            }

        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });

    }

    function addEvent(boardId) {
        $('#saveBtn').click(function () {

            if(utils.isEmpty($('#title').val())) {
                Swal.fire({title: 'Warning', text: '글 제목을 입력 해주세요', icon: 'warning'});
                return;
            }

            let boardItem = {};
            let type, url;
            boardItem.registerId = window.id;
            boardItem.title = $('#title').val();
            boardItem.content = $('#editor').summernote('code');
            boardItem.boardType = board.boardType;

            if(boardId !== null) {
                boardItem.id = boardId;
                type = 'put';
                url = '/board/' + boardId;
            } else {
                type = 'post';
                url = '/board/';
            }

            ajaxUtil.makeAjax(type, url, JSON.stringify(boardItem), $('#editor')).done(function (msg) {
                Swal.fire({title: 'success', text: '저장에 성공하였습니다', icon: 'success'});
                dt_ajax.ajax.reload();
            }).fail(function (xhr, textStatus) {
                console.log(xhr);
                console.log(textStatus);
                Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
            });

            $('#modalScrollable').modal('hide');
            $('#editor').summernote('destroy');
        });

        $('#editBtn').click(function () {
            summerNote.init($('#editor'), 'PREMIER', 'notice');
        });
    }

    this.openBoardContent = function(boardId) {
        $('#boardModal').empty();

        ajaxUtil.get('/board/' + boardId, null).done(function (msg) {
            let template = new EJS({url: 'cportalJS/common/board/ejs/boardContentModal.ejs'}).render(msg , {modalSize : board.modalSize});
            $('#boardModal').append(template);
            $('#title').val(msg.title).prop('readonly', true);

            if (!utils.isEmpty(msg.content)) {
                $('#editor').summernote('code', msg.content);
                $('#editor').summernote('destroy');
            }

            if(msg.registerId !== parseInt(window.id)){
                $('#saveBtn').hide();
                $('#editBtn').hide();
            } else {
                addEvent(boardId);
            }

            $('#modalScrollable').modal('show');

        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });
    }
    

    return board;
});
