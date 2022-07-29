require.config({
    baseUrl: '/cportalJS'
});

define(['jobcast/job/job', 'jobcast/mypage/mypage', 'jobcast/resume/coming'], function (
    job, mypage, coming) {

    let jobcastMain = {};

    jobcastMain.createJob = function (wantedType) {
        job.init(wantedType);
        if (feather) { feather.replace({width : 14, height: 14});}
    }

    jobcastMain.createMyPage = function () {
        mypage.init();
        if (feather) { feather.replace({width : 14, height: 14});}
    }

    jobcastMain.createResume = function () {
        coming.init();
        if (feather) { feather.replace({width : 14, height: 14});}
    }

    jobcastMain.createHunter = function () {
        coming.init();
        if (feather) { feather.replace({width : 14, height: 14});}
    }

    return jobcastMain;
});
