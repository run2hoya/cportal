require.config({
    baseUrl: '/cportalJS'
});

define(['company114/company114Main', 'jobcast/jobcastMain', 'cportal/cportalMain'], function (
    company114Main, jobcastMain, cportalMain) {

    let menuNav = {};
    let stage;

    menuNav.setMenuNav = function (stage) {
        $('#cportal').click(function () { menuNav.goPage(stage, 'cportal');});
        $('#company114').click(function () {menuNav.goPage(stage, 'company114');});
        $('#job').click(function () {menuNav.goPage(stage, 'job');});
        $('#business').click(function () {menuNav.goPage(stage, 'business');});
        $('#alba').click(function () {menuNav.goPage(stage, 'alba');});
        $('#sellCompany').click(function () {menuNav.goPage(stage, 'sellCompany');});
        $('#mypage').click(function () {menuNav.goPage(stage, 'mypage');});
        $('#resume').click(function () {menuNav.goPage(stage, 'resume');});
        $('#hunter').click(function () {menuNav.goPage(stage, 'hunter');});

        $('#viewTable').click(function () {menuNav.goPage(stage, 'viewTable');});
        $('#myDonation').click(function () {menuNav.goPage(stage, 'myDonation');});

    }

    menuNav.goPage = function (stage, page) {

        if(page === 'viewTable')
            location.href='/view';
        if(page === 'myDonation')
            location.href='/myDonation';

        if(stage === 'cportal') {
            $('#contentBody').empty();

            if(page === 'company114') {company114Main.init();}
            else if(page === 'cportal') {cportalMain.init();}
            else if(page === 'job') {jobcastMain.createJob('일자리');}
            else if(page === 'business') {jobcastMain.createJob('일거리');}
            else if(page === 'alba') {jobcastMain.createJob('알바');}
            else if(page === 'sellCompany') {jobcastMain.createJob('사업부 매각');}
            else if(page === 'resume') {jobcastMain.createResume();}
            else if(page === 'hunter') {jobcastMain.createHunter();}
            else if(page === 'mypage') {jobcastMain.createMyPage();}
        } else
            location.href='/main?page=' + page;
    }
    return menuNav;
});
