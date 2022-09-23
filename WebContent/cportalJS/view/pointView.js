require.config({
    baseUrl: 'cportalJS'
});

define(['common/ajaxUtil', 'common/utils'],
    function (ajaxUtil, utils) {

        let pointView = {};
        let dt_ajax;

        pointView.pointViewInit = function () {
            $('#contentMainDiv').empty();
            $('#contentMainDiv').append(new EJS({url: '/cportalJS/view/ejs/pointView/pointView.ejs'}).render());
            if (feather) {feather.replace({width: 14, height: 14});}

            setDonationTable();
        }

        function setDonationTable() {

            let myTable = $('.datatables-ajax');
            if (myTable.length) {
                dt_ajax = myTable.DataTable({
                    processing: true,
                    order: [[ 0, "asc" ]],
                    dom: '<"d-flex justify-content-between align-items-center mx-0 row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>t<"d-flex justify-content-between mx-0 row"<"col-sm-12 col-md-6"i><"col-sm-12 col-md-6"p>>',
                    language: {
                        paginate: {
                            // remove previous & next text from pagination
                            previous: '&nbsp;',
                            next: '&nbsp;'
                        }
                    },
                    ajax : {
                        url : '/view/point/' + window.targetId,
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
                        { data: null },
                        { data: "currentPoint" },
                        { data: "accumulatePoint" },
                        { data: "usePoint" }
                    ],
                    columnDefs: [
                        {
                            targets: 0, render: function (data, type, row) {
                                return '<a href="#" onclick="openPointView(' + data.id + ')">' + data.viewMemberName + '</a>';
                            }
                        },
                        {targets: 1, render: function ( data, type, row ) { return numberWithCommas(data);}},
                        {targets: 2, render: function ( data, type, row ) { return numberWithCommas(data);}},
                        {targets: 3, render: function ( data, type, row ) { return numberWithCommas(data);}}
                    ]
                });
            }

            $('.dataTables_filter .form-control').removeClass('form-control-sm');
            $('.dataTables_length .form-select').removeClass('form-select-sm').removeClass('form-control-sm');
        }

        this.openPointView = function(id) {
            $('#contentDiv').empty();

            let row = dt_ajax.rows( '#' + id ).data()[0];

            $('#contentDiv').append(new EJS({url: '/cportalJS/view/ejs/pointView/pointViewModal.ejs'}).render(row));
            $('#point').val(row.currentPoint);
            $('#accumulatePoint').val(row.accumulatePoint);
            $('#usePoint').val(row.usePoint);

            $('#accumulateBtn').click(function () {

                if(utils.isEmpty($('#accumulate').val())) return false;
                $('#point').val($('#point').val() -  $('#accumulate').val());
                $('#accumulatePoint').val(parseInt($('#accumulatePoint').val()) +  parseInt($('#accumulate').val()));
                $('#accumulate').val(0);
            });

            $('#useBtn').click(function () {
                if(utils.isEmpty($('#use').val())) return false;
                $('#point').val($('#point').val() -  $('#use').val());
                $('#usePoint').val(parseInt($('#usePoint').val()) +  parseInt($('#use').val()));
                $('#use').val(0);
            });

            $('#saveBtn').click(function () {
                let viewPoint = {};
                viewPoint.id = id;
                viewPoint.currentPoint = $('#point').val();
                viewPoint.accumulatePoint = $('#accumulatePoint').val();
                viewPoint.usePoint = $('#usePoint').val();


                ajaxUtil.makeAjax("put", '/view/point/', JSON.stringify(viewPoint), null).done(function(msg){
                    $('#pointViewModal').modal('hide');
                    dt_ajax.ajax.reload();
                    Swal.fire({title: 'success', text: '등록 요청에 성공하였습니다', icon: 'success'});
                }).
                fail(function(xhr, textStatus){
                    dt_ajax.ajax.reload();
                    $('#pointViewModal').modal('hide');
                    console.log(xhr);
                    console.log(textStatus);
                    Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
                });
            });


            $('#pointViewModal').modal('show');
        }


        return pointView;
    });
