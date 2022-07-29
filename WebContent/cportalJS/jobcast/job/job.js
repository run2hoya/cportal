require.config({
    baseUrl: '/cportalJS'
});

define(['common/ajaxUtil', 'common/utils', 'common/imageSend', 'common/payment/payment'],
    function (ajaxUtil, utils, imageSend, payment) {

        let job = {};
        let dt_ajax;

        job.init = function (wantedType) {

            $('#contentBody').append(new EJS({url: '/cportalJS/jobcast/job/ejs/job.ejs'}).render(
                {type: wantedType}
            ));

            addPremierJob(wantedType);
            addNormalJob(wantedType);
        }


        function addPremierJob(wantedType) {

            let url = '/wanted/viewer/'+ wantedType + '?date=' + moment().format('YYYY-MM-DD') + '&productType=PREMIER';
            ajaxUtil.makeAjax("get", url, null, $('#contentBody')).done(function (msg) {
                console.log(msg);

                for (var i = 0, wanted; wanted = msg[i]; i++) {
                    let day = 'D - ' + (moment(wanted.endDate).diff(moment(), 'days') + 1) + '일';
                    $('#premier').append(new EJS({url: '/cportalJS/jobcast/job/ejs/premierItem.ejs'}).render(
                        {days: day}, wanted));
                }

                $('.premierItem').on('click', function () {
                    window.open('./wanted/popup/view/' + $(this).attr('id'), "_blank");
                });

            }).fail(function (xhr, textStatus) {
                console.log(xhr);
                console.log(textStatus);
                Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
            });
        }

        function addNormalJob(wantedType) {

            let table = $('.datatables-ajax');
            if (table.length) {
                dt_ajax = table.DataTable({
                    processing: true,
                    order     : [[6, "desc"]],
                    dom       : '<"d-flex justify-content-between align-items-center mx-0 row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>t<"d-flex justify-content-between mx-0 row"<"col-sm-12 col-md-6"i><"col-sm-12 col-md-6"p>>',
                    language  : {
                        paginate: {
                            // remove previous & next text from pagination
                            previous: '&nbsp;',
                            next    : '&nbsp;'
                        }
                    },
                    ajax      : {
                        url        : '/wanted/viewer/' + wantedType + '?date=' + moment().format('YYYY-MM-DD') + '&productType=NORMAL',
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
                        {data: "title"},
                        {data: "jobType"},
                        {data: "viewCnt"},
                        {data: "candidateCnt"},
                        {data: "likeCnt"},
                        {data: null},
                        {data: "registDate"}

                    ],
                    columnDefs: [
                        {
                            targets: 5, render: function (data, type, row) {
                                return '<span class="badge rounded-pill badge-light-primary">' +
                                    (moment(data.endDate).diff(moment(), 'days') +1 ) + '일' + '</span>';
                            }
                        },
                        {
                            // Actions
                            targets  : 7,
                            orderable: false,
                            render   : function (data, type, full, meta) {
                                return (
                                    '<a href="#" class="item-edit">' + feather.icons['file-text'].toSvg({class: 'font-small-4'}) + '</a>'
                                );
                            }
                        }
                    ]
                });
            }

            $('.dataTables_filter .form-control').removeClass('form-control-sm');
            $('.dataTables_length .form-select').removeClass('form-select-sm').removeClass('form-control-sm');
            $('.datatables-ajax tbody').on('click', '.item-edit', function () {
                console.log(dt_ajax.row($(this).parents('tr')).data());
                let wantedId = dt_ajax.row($(this).parents('tr')).data().id;
                window.open('./wanted/popup/view/' + wantedId, "_blank");
            });
        }

        return job;
    });
