require.config({
    baseUrl: '/cportalJS',
    paths: {
        'amcharts5' : '/assets/amcharts5'
    }
});

define(['common/ajaxUtil', 'common/utils', 'common/board/board', 'amcharts5/index',
    'amcharts5/xy', 'amcharts5/themes/Animated'], function (
    ajaxUtil, utils, board) {

    let caportalMain = {};

    caportalMain.init = function (wantedType) {
        $('#contentBody').append(new EJS({url: '/cportalJS/cportal/ejs/cportalMain.ejs'}).render(
        ));
        createStat();
        createBoard();
        createJobcastChart();
    }

    function createBoard() {
        board.createBoard($('#noticeBoard'), 'CPORTAL_NOTI', '공지사항', 'modal-lg', false, 6);
    }

    function createStat() {
        ajaxUtil.makeAjax("get", '/cportal/stat', null, null).done(function (msg) {
            console.log(msg);

            $('#stat').append(new EJS({url: '/cportalJS/cportal/ejs/stat.ejs'}).render(msg));
            if (feather) { feather.replace({width : 14, height: 14});}
        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });
    }
    function createJobcastChart() {

        ajaxUtil.makeAjax("get", '/cportal/jobcast/chart', null, $('#chartdiv')).done(function (msg) {
            console.log(msg);
            am5.ready(function() {
                var root = am5.Root.new("chartdiv");
                root.setThemes([am5themes_Animated.new(root)]);

                var chart = root.container.children.push(am5xy.XYChart.new(root, {
                    panX: true,
                    panY: true,
                    wheelX: "panX",
                    wheelY: "zoomX",
                    pinchZoomX:true
                }));

                var cursor = chart.set("cursor", am5xy.XYCursor.new(root, {}));
                cursor.lineY.set("visible", false);

                var xRenderer = am5xy.AxisRendererX.new(root, { minGridDistance: 30 });
                xRenderer.labels.template.setAll({
                    rotation: -90,
                    centerY: am5.p50,
                    centerX: am5.p100,
                    paddingRight: 15
                });

                var xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(root, {
                    maxDeviation: 0.3,
                    categoryField: "name",
                    renderer: xRenderer,
                    tooltip: am5.Tooltip.new(root, {})
                }));

                var yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
                    maxDeviation: 0.3,
                    renderer: am5xy.AxisRendererY.new(root, {})
                }));

                var series = chart.series.push(am5xy.ColumnSeries.new(root, {
                    name: "Series 1",
                    xAxis: xAxis,
                    yAxis: yAxis,
                    valueYField: "value",
                    sequencedInterpolation: true,
                    categoryXField: "name",
                    tooltip: am5.Tooltip.new(root, {
                        labelText:"{valueY}"
                    })
                }));

                series.columns.template.setAll({ cornerRadiusTL: 5, cornerRadiusTR: 5 });
                series.columns.template.adapters.add("fill", function(fill, target) {
                    return chart.get("colors").getIndex(series.columns.indexOf(target));
                });

                series.columns.template.adapters.add("stroke", function(stroke, target) {
                    return chart.get("colors").getIndex(series.columns.indexOf(target));
                });

                xAxis.data.setAll(msg);
                series.data.setAll(msg);
                series.appear(1000);
                chart.appear(1000, 100);

            }); // end am5.ready()

        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });

    }


    return caportalMain;
});
