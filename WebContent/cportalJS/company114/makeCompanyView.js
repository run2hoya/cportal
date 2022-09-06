require.config({
    baseUrl: '/cportalJS',
});

define(['common/ajaxUtil', 'common/utils', 'common/summerNote'], function (ajaxUtil, utils, summerNote) {

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

            if (!utils.isEmpty(msg.platinum)) {
                totalCompanyCount += msg.platinum.length;
            }
            makePlatinumCompany(msg.platinum, $('#platinumDivId'));
            addHolo();

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
            summerNote.init($('#editor'), makeCompanyView.productType, 'company114');
        });

        var clipboard = new ClipboardJS('#shareBtn', {container: document.getElementById('exampleModalScrollable')});
        clipboard.on( 'success', function() {Swal.fire({title: 'SUCCESS', text: '주소가 복사되었습니다.', icon: 'success'});} );
        clipboard.on( 'error', function() {Swal.fire({title: 'ERROR', text: '해당 브라우저는 복사 기능이 지원되지 않습니다', icon: 'error'});} );
    }

    function makePlatinumCompany(platinum, div) {

        let cnt = 0;
        platinum.forEach((value) => {
            let template = new EJS({url: 'cportalJS/company114/ejs/platinum.ejs'}).render(value);
            div.append(template);
            cnt++;
        })

        if(cnt < 3) {
            for(cnt; cnt < 3; cnt++) {
                let template = new EJS({url: 'cportalJS/company114/ejs/platinum.ejs'}).render(
                    {id : "empty", companybg : "/cportalFile/img/p8.jpg",
                        companyName : "플래티넘 사업부",
                        companyDes : "3개의 사업부만 모십니다. 많은 관심 부탁드리겠습니다."}
                );
                div.append(template);
            }
        }

        $(".holo").on("click", function (e) {
            if($(this).attr("id") !== "empty") {
                makeCompanyView.companyId = $(this).attr("id");
                openCompanyInfo($(this).attr("id"));
            }
        });
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
        ajaxUtil.get('/company/info/' + companyId, $(".modal-body")).done(function (msg) {
            console.log(msg);
            if (!utils.isEmpty(msg)) {
                let template = new EJS({url: 'cportalJS/company114/ejs/companyInfoModal.ejs'}).render(
                    msg, {companyId : makeCompanyView.companyId});
                $('#companyInfoModal').append(template);

                if (!utils.isEmpty(msg.content)) {
                    $('#editor').summernote('code', msg.content);
                    $('#editor').summernote('destroy');
                }

                if(msg.registerId !== parseInt(window.id)){
                    $('#saveBtn').hide();
                    $('#editBtn').hide();
                } else {
                    $('#saveBtn').show();
                    $('#editBtn').show();
                }

                makeCompanyView.productType = msg.productType;
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
            let title = value.companyName + "<br> 대표 : " + value.companyCeo + "<br> email : " + value.companyEmail
            + "<br> phone : " + value.phone + "<br> 한줄 소개 : " + value.companyDes;
            div.append('<button type="button" class="btn btn-outline-secondary round waves-effect tip" data-bs-html="true" data-bs-toggle="tooltip" data-bs-placement="top" ' +
                'title="' + title + '" data-bs-trigger="click">' +
                value.companyName + '</button>');
        });

        $(".tip").tooltip();
    }

    function updateContent(companyId, content) {
        let editContentDto = {};
        editContentDto.id = companyId;
        editContentDto.content = content;

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

    function addHolo() {
        var x;
        var $cards = $(".holo");
        var $style = $(".hover");

        $cards
            .on("mousemove touchmove", function (e) {
                // normalise touch/mouse
                var pos = [e.offsetX, e.offsetY];
                e.preventDefault();
                if (e.type === "touchmove") {
                    pos = [e.touches[0].clientX, e.touches[0].clientY];
                }
                var $card = $(this);
                // math for mouse position
                var l = pos[0];
                var t = pos[1];
                var h = $card.height();
                var w = $card.width();
                var px = Math.abs(Math.floor((100 / w) * l) - 100);
                var py = Math.abs(Math.floor((100 / h) * t) - 100);
                var pa = 50 - px + (50 - py);
                // math for gradient / background positions
                var lp = 50 + (px - 50) / 1.5;
                var tp = 50 + (py - 50) / 1.5;
                var px_spark = 50 + (px - 50) / 7;
                var py_spark = 50 + (py - 50) / 7;
                var p_opc = 20 + Math.abs(pa) * 1.5;
                var ty = ((tp - 50) / 2) * -1;
                var tx = ((lp - 50) / 1.5) * 0.5;
                // css to apply for active card
                var grad_pos = `background-position: ${lp}% ${tp}%;`;
                var sprk_pos = `background-position: ${px_spark}% ${py_spark}%;`;
                var opc = `opacity: ${p_opc / 100};`;
                var tf = `transform: rotateX(${ty}deg) rotateY(${tx}deg)`;
                // need to use a <style> tag for psuedo elements
                var style = `.holo:hover:before { ${grad_pos} }  /* gradient */
      .holo:hover:after { ${sprk_pos} ${opc} }   /* sparkles */ 
    `;
                // set / apply css class and style
                $cards.removeClass("active");
                $card.removeClass("animated");
                $card.attr("style", tf);
                $style.html(style);
                if (e.type === "touchmove") {
                    return false;
                }
                clearTimeout(x);
            })
            .on("mouseout touchend touchcancel", function () {
                // remove css, apply custom animation on end
                var $card = $(this);
                $style.html("");
                $card.removeAttr("style");
                x = setTimeout(function () {
                    $card.addClass("animated");
                }, 2500);
            });
    }

    return makeCompanyView;
});
