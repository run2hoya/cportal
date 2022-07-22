require.config({
    baseUrl: 'cportalJS'
});

define(['common/ajaxUtil', 'common/utils'], function (ajaxUtil, utils) {

    let job = {};

    job.create = function () {
        job.init();
    }

    return job;
});
