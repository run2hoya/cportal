require.config({
    baseUrl: 'cportalJS'
});

define(['common/ajaxUtil', 'common/utils'],
    function (ajaxUtil, utils) {

        let donationChance = {};
        let dt_ajax;

        donationChance.donationChanceInit = function () {
            $('#contentMainDiv').empty();
            $('#contentMainDiv').append(new EJS({url: '/cportalJS/view/ejs/donationChance/donationMainView.ejs'}).render());
            setDonationTable();
            makeChart();
        }

        function setDonationTable() {
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
                dt_ajax.ajax.url('/donation/viewTable/' + window.targetId + '?startDate=' + start.format("YYYY-MM-DD") + '&endDate=' + end.format("YYYY-MM-DD")).load();
            });

            let myTable = $('.datatables-ajax');
            if (myTable.length) {
                dt_ajax = myTable.DataTable({
                    processing: true,
                    order: [[ 5, "desc" ]],
                    dom: '<"d-flex justify-content-between align-items-center mx-0 row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>t<"d-flex justify-content-between mx-0 row"<"col-sm-12 col-md-6"i><"col-sm-12 col-md-6"p>>',
                    language: {
                        paginate: {
                            // remove previous & next text from pagination
                            previous: '&nbsp;',
                            next: '&nbsp;'
                        }
                    },
                    ajax : {
                        url : '/donation/viewTable/' + window.targetId + '?startDate=' + start.format("YYYY-MM-DD") + '&endDate=' + end.format("YYYY-MM-DD"),
                        dataSrc : function ( data ) {

                            for(var i=0, row; row=data[i]; i++) {
                                row.DT_RowId = row.id;
                            }

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
                        { data: null, "width": "30%"},
                        { data: "viewMemberName" },
                        { data: "point" },
                        { data: "givePoint" },
                        { data: "donationState" },
                        { data: "publishDate" }

                    ],
                    columnDefs: [
                        {
                            targets: 0, render: function (data, type, row) {
                                return '<a href="#" onclick="openDonation(' + data.id + ')">' + data.ownerName + '</a>';
                            }
                        },
                        {targets: 2, render: function ( data, type, row ) { return numberWithCommas(data);}},
                        {targets: 3, render: function ( data, type, row ) { return numberWithCommas(data);}},
                        {targets: 4, render: function ( data, type, row ) {
                                if(data === 'PUBLISH') { return '<span class="badge rounded-pill badge-light-primary">발급</span>'}
                                else if(data === 'DONATION') { return '<span class="badge rounded-pill badge-light-warning">기부 완료</span>'}
                                else if(data === 'COMPLETE') { return '<span class="badge rounded-pill badge-light-success">처리 완료</span>'}
                                else { return '<span class="badge rounded-pill badge-light-danger">UNKNOWN</span>'}
                            }}
                    ]
                });
            }

            $('.dataTables_filter .form-control').removeClass('form-control-sm');
            $('.dataTables_length .form-select').removeClass('form-select-sm').removeClass('form-control-sm');

        }

        this.openDonation = function(id) {
            $('#contentDiv').empty();

            let row = dt_ajax.rows( '#' + id ).data()[0];

            $('#contentDiv').append(new EJS({url: '/cportalJS/view/ejs/donationChance/donationModal.ejs'}).render(row));
            $('#point').val(row.point);
            $('#givePoint').val(row.givePoint);
            $('#plus').val(row.plus);
            $('#multiply').val(row.multiply);

            $('#plus').keyup(function () {
                calPoint();
            });
            $('#multiply').keyup(function () {
                calPoint();
            });

            calPoint();

            $('#donationModal').modal('show');
        }

        function calPoint() {
            let savePoint =  (parseInt($('#givePoint').val()) + parseInt($('#plus').val()) ) *
                parseInt($('#multiply').val());
            $('#savePoint').val(savePoint);
        }

        function makeChart() {
            // Goal Overview  Chart
            // -----------------------------
            let goalChartOptions = {
                chart: {
                    height: 245,
                    type: 'radialBar',
                    sparkline: {
                        enabled: true
                    },
                    dropShadow: {
                        enabled: true,
                        blur: 3,
                        left: 1,
                        top: 1,
                        opacity: 0.1
                    }
                },
                colors: ['#51e5a8'],
                plotOptions: {
                    radialBar: {
                        offsetY: -10,
                        startAngle: -150,
                        endAngle: 150,
                        hollow: {
                            size: '77%'
                        },
                        track: {
                            background: '#ebe9f1',
                            strokeWidth: '50%'
                        },
                        dataLabels: {
                            name: {
                                show: false
                            },
                            value: {
                                color: '#5e5873',
                                fontSize: '2.86rem',
                                fontWeight: '600'
                            }
                        }
                    }
                },
                fill: {
                    type: 'gradient',
                    gradient: {
                        shade: 'dark',
                        type: 'horizontal',
                        shadeIntensity: 0.5,
                        gradientToColors: [window.colors.solid.success],
                        inverseColors: true,
                        opacityFrom: 1,
                        opacityTo: 1,
                        stops: [0, 100]
                    }
                },
                series: [50],
                stroke: {
                    lineCap: 'round'
                },
                grid: {
                    padding: {
                        bottom: 30
                    }
                }
            };
            let goalChart = new ApexCharts(document.querySelector('#goal-overview-chart'), goalChartOptions);
            goalChart.render();
        }

        return donationChance;
    });
