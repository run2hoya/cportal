require.config({
    baseUrl: 'cportalJS'
});

define(['common/ajaxUtil', 'common/utils'], function (ajaxUtil, utils) {

    let makeCompanyView = {};
    let companyId;
    let productType;
    let maxChar = 4000;

    makeCompanyView.createCompanyView = function () {
        createCompanyByProductType();
        createCompanyChart();
    }


    function createCompanyByProductType() {
        ajaxUtil.makeAjax("get", './company/title', null, $('#companyList')).done(function (msg) {
            let totalCompanyCount = 0;
            if (!utils.isEmpty(msg.premier)) {
                $('#premierCompanyCnt').text(msg.premier.length);
                totalCompanyCount += msg.premier.length;
                makePremierCompany(msg.premier, $('#premierDivId'));
            }
            if (!utils.isEmpty(msg.normal)) {
                $('#normalCompanyCnt').text(msg.normal.length);
                totalCompanyCount += msg.normal.length;
                makeNormalCompany(msg.normal, $('#normalDivId'));
            }
            if (!utils.isEmpty(msg.etc)) {
                $('#companyCnt').text(msg.etc.length);
                totalCompanyCount += msg.etc.length;
                makeETCCompany(msg.etc, $('#etcDivId'));
            }
            $('#totalCompanyCnt').text(totalCompanyCount);
        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });
    }

    function addEvent() {
        $('#saveBtn').click(function () {
            updateContent(makeCompanyView.companyId, $('#editor').summernote('code'));
            $('#editor').summernote('destroy');
        });

        $('#editBtn').click(function () {

            let option;
            if (makeCompanyView.productType === 'PREMIER') {
                option = ['insert', ['picture', 'link', 'hr']];
            } else {
                option = ['insert', ['link', 'hr']]
            }

            let config = {
                toolbar  : [
                    // [groupName, [list of button]]
                    ['fontname', ['fontname']],
                    ['fontsize', ['fontsize']],
                    ['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
                    ['color', ['forecolor', 'color']],
                    ['table', ['table']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['height', ['height']],
                    option

                ],
                fontNames: ['Arial', 'Arial Black', 'NotoSansKR', 'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋움체', '바탕체', 'sc'],
                fontSizes: ['8', '9', '10', '11', '12', '14', '16', '18', '20', '22', '24', '28', '30', '36', '50', '72'],
                lang     : "ko-KR",
                height   : 540,
                focus    : true,
                callbacks: {
                    onImageUpload: function (files, editor, welEditable) {
                        for (let i = files.length - 1; i >= 0; i--) {
                            sendFile(files[i], this);
                        }
                    },
                    onMediaDelete: function (target) {
                        deleteFile(target[0].src);
                    }
                }
            };

            $('#editor').summernote(config);
            $('#editor').summernote('fontSize', 16);
            $('#editor').summernote('fontName', 'NotoSansKR');

        });
        // $('#saveBtn').hide();
        // $('#editBtn').hide();
    }

    function makePremierCompany(premier, div) {

        premier.forEach((value) => {
            let template = new EJS({url: 'cportalJS/company114/ejs/premier.ejs'}).render(value);
            div.append(template);
        })

        $(".options").children().first().addClass("active");
        $(".option").mouseover(function () {
            $(".option").removeClass("active");
            $(this).addClass("active");
        });

        $(".option").on("click", function (e) {
            makeCompanyView.companyId = $(this).attr("id");
            openCompanyInfo($(this).attr("id"));
        });
    }

    function openCompanyInfo(companyId) {
        $('#companyInfoModal').empty();

        $('#editor').text('');
        ajaxUtil.get('./company/' + companyId, $(".modal-body")).done(function (msg) {
            console.log(msg);
            if (!utils.isEmpty(msg)) {
                let template = new EJS({url: 'cportalJS/company114/ejs/companyInfoModal.ejs'}).render(msg);
                $('#companyInfoModal').append(template);

                if (!utils.isEmpty(msg.content)) {
                    $('#editor').summernote('code', msg.content);
                    $('#editor').summernote('destroy');
                }

                makeCompanyView.productType = msg.companyProductType;
                addEvent();
                $('#exampleModalScrollable').modal('show');
            }
        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });
    }

    function makeNormalCompany(normal, div) {
        normal.forEach((value) => {
            let template = new EJS({url: 'cportalJS/company114/ejs/normal.ejs'}).render(value);
            div.append(template);
        })

        $(".col").on("click", function (e) {
            makeCompanyView.companyId = $(this).attr("id");
            openCompanyInfo($(this).attr("id"));
        });
    }

    function makeETCCompany(etc, div) {
        etc.forEach((value) => {
            div.append('<button type="button" class="btn btn-outline-secondary round waves-effect">' + value.companyName + '</button>');
        })
    }

    function updateContent(companyId, content) {
        let editContentDto = {};
        editContentDto.id = companyId;
        editContentDto.content = content;

        console.log(JSON.stringify(editContentDto));

        ajaxUtil.makeAjax("put", './company/' + companyId + '/content', JSON.stringify(editContentDto), $('#editor')).done(function (msg) {
            console.log(msg);
        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });
    }

    function replaceAll(str, searchStr, replaceStr) {
        return str.split(searchStr).join(replaceStr);
    }


    function deleteFile(file) {
        // file = file.substring(file.indexOf("company114"));
        // file = replaceAll(file,'/', '_');
        console.log(file);
        ajaxUtil.makeAjax('delete', './upload/file?fileName=' + file, null, null).done(function (msg) {
            console.log(msg);
        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
        });
    }

    function sendFile(file, el) {

        if (makeCompanyView.productType !== 'PREMIER') {
            Swal.fire({title: 'ERROR', text: '해당 상품은 이미지를 이용 할 수 없습니다.', icon: 'error'});
            return;
        }

        let form_data = new FormData();
        form_data.append('file', file);
        $.ajax({
            data       : form_data,
            type       : "POST",
            dataType   : 'text',
            url        : 'upload/file/COMPANY114/editor',
            cache      : false,
            contentType: false,
            enctype    : 'multipart/form-data',
            processData: false,
            success    : function (img_name) {
                $(el).summernote('editor.insertImage', img_name);
            },
            error      : function (xhr, textStatus, error) {
                console.log(xhr);
                console.log(textStatus);
                console.log(error);
                Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
                return false;
            }
        });
    }

    function createCompanyChart() {

        ajaxUtil.makeAjax("get", './company/chart/type', null, $('#donut-chart')).done(function (msg) {
            var donutChartEl = document.querySelector('#donut-chart'),
                donutChartConfig = {
                    chart      : {
                        height: 350,
                        type  : 'donut'
                    },
                    legend     : {
                        show    : true,
                        position: 'bottom'
                    },
                    labels     : msg.labels,
                    series     : msg.series,
                    dataLabels : {
                        enabled  : true,
                        formatter: function (val, opt) {
                            return parseInt(val) + '%';
                        }
                    },
                    plotOptions: {
                        pie: {
                            donut: {
                                labels: {
                                    show : true,
                                    name : {
                                        fontSize  : '2rem',
                                        fontFamily: 'Montserrat'
                                    },
                                    value: {
                                        fontSize  : '1rem',
                                        fontFamily: 'Montserrat',
                                        formatter : function (val) {
                                            return parseInt(val) + ' 사업부';
                                        }
                                    },
                                    total: {
                                        show     : true,
                                        fontSize : '1.5rem',
                                        label    : 'Castis',
                                        formatter: function (w) {
                                            return '사업부';
                                        }
                                    }
                                }
                            }
                        }
                    },
                    responsive : [
                        {
                            breakpoint: 992,
                            options   : {
                                chart: {
                                    height: 380
                                }
                            }
                        },
                        {
                            breakpoint: 576,
                            options   : {
                                chart      : {
                                    height: 320
                                },
                                plotOptions: {
                                    pie: {
                                        donut: {
                                            labels: {
                                                show : true,
                                                name : {
                                                    fontSize: '1.5rem'
                                                },
                                                value: {
                                                    fontSize: '1rem'
                                                },
                                                total: {
                                                    fontSize: '1.5rem'
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    ]
                };

            if (typeof donutChartEl !== undefined && donutChartEl !== null) {
                let donutChart = new ApexCharts(donutChartEl, donutChartConfig);
                donutChart.render();
            }

        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });
    }


    return makeCompanyView;
});
