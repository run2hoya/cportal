require.config({
    baseUrl: 'cportalJS'
});

require(['company114/company114Main', 'jobcast/jobcastMain'], function (
    company114Main, jobcastMain) {

    let id = null;

    function init() {
        addMenuEvent();
        company114Main.init();
    }

    function addMenuEvent() {

        $('#cportal').click(function () {
            $('#contentBody').empty();
            company114Main.init();
        });

        $('#company114').click(function () {
            $('#contentBody').empty();
            company114Main.init();
        });

        $('#job').click(function () {
            $('#contentBody').empty();
            jobcastMain.createJob();
        });

    }


    init();
});
