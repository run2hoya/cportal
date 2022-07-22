require.config({
    baseUrl: 'cportalJS'
});

define(['jobcast/job'], function (job) {

    let jobcastMain = {};

    jobcastMain.createJob = function () {
        job.init();

        if (feather) { feather.replace({width : 14, height: 14});}
    }

    return jobcastMain;
});
