require.config({
    baseUrl: 'cportalJS'
});

define(['common/ajaxUtil', 'common/utils', 'common/payment/payment'],
    function (ajaxUtil, utils, payment) {

        let mypage = {};
        let dt_ajax;

        mypage.init = function () {

            $('#contentBody').append(new EJS({url: 'cportalJS/jobcast/mypage/ejs/mypageMain.ejs'}).render());
            addEvent();
            setMyWanted();
        }

        function setMyWanted() {
            let start = moment().subtract(6, 'month');
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
                dt_ajax.ajax.url('/wanted/register/' + window.id + '?startDate=' + start.format("YYYY-MM-DD") + '&endDate=' + end.format("YYYY-MM-DD")).load();
            });

            let myWantedTable = $('.datatables-ajax');
            if (myWantedTable.length) {
                dt_ajax = myWantedTable.DataTable({
                    processing: true,
                    order: [[ 7, "desc" ]],
                    dom: '<"d-flex justify-content-between align-items-center mx-0 row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>t<"d-flex justify-content-between mx-0 row"<"col-sm-12 col-md-6"i><"col-sm-12 col-md-6"p>>',
                    language: {
                        paginate: {
                            // remove previous & next text from pagination
                            previous: '&nbsp;',
                            next: '&nbsp;'
                        }
                    },
                    ajax : {
                        url : '/wanted/register/' + window.id + '?startDate=' + start.format("YYYY-MM-DD") + '&endDate=' + end.format("YYYY-MM-DD"),
                        dataSrc : function ( data ) {
                            return data;
                        },
                        type: "get",
                        contentType : "application/json; charset=UTF-8",
                        error : function(xhr, textStatus, error){
                            console.log(xhr);
                            console.log(textStatus);
                            console.log(error);
                            Swal.fire({title: 'ERROR', text: '정상적인 접근이 아닙니다.', icon: 'error'});
                        }
                    },
                    columns: [
                        { data: "wantedType" },
                        { data: "productType" },
                        { data: "title" },
                        { data: "startDate" },
                        { data: "endDate" },
                        { data: "open" },
                        { data: null },
                        { data: "registDate" }

                    ],
                    columnDefs: [
                    {targets: 6, render: function ( data, type, row ) { return data.viewCnt + "/" + data.candidateCnt + "/" + data.likeCnt;}},
                    {targets: 5, render: function ( data, type, row ) {
                        if(data) { return '<span class="badge rounded-pill badge-light-primary">공개</span>'}
                        else { return '<span class="badge rounded-pill badge-light-warning">비공개</span>'}
                    }},
                    {
                        // Actions
                        targets: 8,
                        orderable: false,
                        render: function (data, type, full, meta) {
                            return (
                                '<a href="#" class="item-edit">' + feather.icons['edit'].toSvg({ class: 'font-small-4' }) + '</a>'
                            );
                        }
                    }
                ]
                });
            }

            $('.dataTables_filter .form-control').removeClass('form-control-sm');
            $('.dataTables_length .form-select').removeClass('form-select-sm').removeClass('form-control-sm');
            $('.datatables-ajax tbody').on('click', '.item-edit', function () {
                console.log( dt_ajax.row( $(this).parents('tr') ).data() );
                let wantedId = dt_ajax.row( $(this).parents('tr') ).data().id;
                window.open('/popup/wanted/edit/' + wantedId, "_blank");
            });

        }

        function addEvent() {

            $('#wantedWorker').click(function () {
                showModal();
                $('#wantedType').text('일자리');
                $('#wantedTitle').text('일자리 공고 Title');
            });

            $('#wantedWork').click(function () {
                showModal();
                $('#wantedType').text('일거리');
                $('#wantedTitle').text('일거리 공고 Title');
            });

            $('#wantedAlba').click(function () {
                showModal();
                $('#wantedType').text('알바');
                $('#wantedTitle').text('알바 공고 Title');
            });

            $('#wantedSellCompany').click(function () {
                showModal();
                $('#wantedType').text('사업부 매각');
                $('#wantedTitle').text('사업부 매각 공고 Title');
            });
        }

        function showModal() {
            $('#modal').empty();
            addModal();
            new bootstrap.Modal($('#registerModal')).show();
            if (feather) {feather.replace({width: 14, height: 14});}
        }
        function setEndDateAndPriceInit() {
            $('#endDate').val('');
            $('#startDate').val('');
            $('#week').val('');
            $('#price').html(0);
        }

        function setEndDateAndPrice() {
            if (utils.isEmpty($('#week').val())) {
                $('#endDate').val('');
                return;
            }
            if (utils.isEmpty($('#startDate').val())) {
                $('#endDate').val('');
                return;
            }

            let date = new Date($('#startDate').val());
            date.setDate(date.getDate() + ($('#week').val() * 7));
            $('#endDate').val(date.toISOString().slice(0, 10));

            let price = 40000;
            if ($('input[name=productType]:checked').val() === 'NORMAL')
                price = 15000;

            new numberCounter("price", $('#week').val() * price);
        }

        function addModal() {

            $('#modal').append(new EJS({url: 'cportalJS/jobcast/mypage/ejs/registerModal.ejs'}).render());

            var bsStepper = document.querySelectorAll('.bs-stepper');
            if (typeof bsStepper !== undefined && bsStepper !== null) {
                for (var el = 0; el < bsStepper.length; ++el) {
                    bsStepper[el].addEventListener('show.bs-stepper', function (event) {
                        var index = event.detail.indexStep;
                        var numberOfSteps = $(event.target).find('.step').length - 1;
                        var line = $(event.target).find('.step');

                        for (var i = 0; i < index; i++) {
                            line[i].classList.add('crossed');

                            for (var j = index; j < numberOfSteps; j++) {
                                line[j].classList.remove('crossed');
                            }
                        }
                        if (event.detail.to == 0) {
                            for (var k = index; k < numberOfSteps; k++) {
                                line[k].classList.remove('crossed');
                            }
                            line[0].classList.remove('crossed');
                        }
                    });
                }
            }

            let horizontalWizard = document.querySelector('.horizontal-wizard-example');
            if (typeof horizontalWizard !== undefined && horizontalWizard !== null) {

                let numberedStepper = new Stepper(horizontalWizard);
                $(horizontalWizard)
                    .find('.btn-next')
                    .each(function () {
                        $(this).on('click', function (e) {
                            numberedStepper.next();
                        });
                    });

                $(horizontalWizard)
                    .find('.btn-prev')
                    .on('click', function () {
                        numberedStepper.previous();
                    });


                $(horizontalWizard)
                    .find('.btn-submit')
                    .on('click', function () {

                        let wantedForm = $('#wantedForm');
                        if (wantedForm.length) {
                            wantedForm.validate({
                                rules        : {
                                    startDate  : {
                                        required: true
                                    },
                                    week  : {
                                        required: true
                                    },
                                    title   : {
                                        required : true,
                                        maxlength: 50
                                    }
                                },
                                messages     : {
                                    title: {
                                        required : "Title를 입력해 주세요",
                                        maxlength: "최대 {0}글자이하이어야 합니다"
                                    }
                                }
                            });
                        }

                        var isValid = wantedForm.valid();
                        if (isValid) {

                            let wantedRegisterDto = {};
                            wantedRegisterDto.registerId = window.id;
                            wantedRegisterDto.title = $('#title').val();
                            wantedRegisterDto.wantedType = $('#wantedType').text();
                            wantedRegisterDto.productType = $('input[name=productType]:checked').val();
                            wantedRegisterDto.startDate = $('#startDate').val();
                            wantedRegisterDto.endDate = $('#endDate').val();

                            ajaxUtil.makeAjax("post", './wanted', JSON.stringify(wantedRegisterDto), null).done(function(msg){
                                Swal.fire({title: 'success', text: '등록 요청에 성공하였습니다', icon: 'success'});
                                dt_ajax.ajax.reload();
                            }).
                            fail(function(xhr, textStatus){
                                console.log(xhr);
                                console.log(textStatus);
                                Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
                            });

                            //TODO 결제하기 구현 master
                            //payment.create(null, null, $('#modal'));
                            $('#registerModal').modal('hide');

                        }

                    });
            }

            flatpickr.localize(flatpickr.l10ns.ko);
            flatpickr($('#startDate'));
            $('#startDate').flatpickr({local: 'ko'});

            $('#week').keyup(function () {
                setEndDateAndPrice();
            });

            $("#startDate").change(function () {
                setEndDateAndPrice();
            });

            $("input[name=productType]:radio").change(function () {
                setEndDateAndPriceInit();
            });
        }


        return mypage;
    });
