require.config({
    baseUrl: 'cportalJS'
});

define(['common/ajaxUtil', 'common/utils', 'common/imageSend', 'common/payment/payment'],
    function (ajaxUtil, utils, imageSend, payment) {

    let job = {};

    job.init = function () {

        $('#contentBody').append(new EJS({url: 'cportalJS/jobcast/jobEJS/job.ejs'}).render());
        $('#stat').append(new EJS({url: 'cportalJS/jobcast/jobEJS/stat.ejs'}).render());

        addModal();
        addEvent();
        addPremierJob();
    }

    function addEvent() {


        flatpickr.localize(flatpickr.l10ns.ko);
        flatpickr($('#startDate'));
        $('#startDate').flatpickr({    local: 'ko'});

        $('#week').keyup(function() {
            setEndDateAndPrice();
        });

        $( "#startDate" ).change(function() {
            setEndDateAndPrice();
        });

        $("input[name='customOptionsCheckableRadios']:radio").change(function () {
            console.log('customOptionsCheckableRadios');
            if($('input[name=customOptionsCheckableRadios]:checked').val() === 'normal')
                $('#imgSelect').hide();
            else
                $('#imgSelect').show();
        });

    }

    function setEndDateAndPrice() {
        if(utils.isEmpty($('#week').val())) {
            $('#endDate').val('');
            return;
        }
        if(utils.isEmpty($('#startDate').val())) {
            $('#endDate').val('');
            return;
        }

        let date = new Date($('#startDate').val());
        date.setDate(date.getDate() + ($('#week').val() * 7));
        $('#endDate').val(date.toISOString().slice(0, 10));

        let price = 30000;
        if($('input[name=customOptionsCheckableRadios]:checked').val() === 'normal')
            price = 15000;

        console.log($('#week').val() * price);

        new numberCounter("price", $('#week').val() * price);
    }

    function addModal() {

        $('#modal').append(new EJS({url: 'cportalJS/jobcast/jobEJS/registerModal.ejs'}).render());
        imageSend.init(400, 400, null, '/upload/file/jobcast/');

        var bsStepper = document.querySelectorAll('.bs-stepper');
        if (typeof bsStepper !== undefined && bsStepper !== null) {
            for (var el = 0; el < bsStepper.length; ++el) {
                bsStepper[el].addEventListener('show.bs-stepper', function (event) {
                    var index = event.detail.indexStep;
                    var numberOfSteps = $(event.target).find('.step').length - 1;
                    var line = $(event.target).find('.step');

                    for (var i = 0; i < index; i++) {
                        line[i].classList.add('crossed');

                        for (var j = index; j < numberOfSteps; j++) {
                            line[j].classList.remove('crossed');
                        }
                    }
                    if (event.detail.to == 0) {
                        for (var k = index; k < numberOfSteps; k++) {
                            line[k].classList.remove('crossed');
                        }
                        line[0].classList.remove('crossed');
                    }
                });
            }
        }

        let horizontalWizard = document.querySelector('.horizontal-wizard-example');
        if (typeof horizontalWizard !== undefined && horizontalWizard !== null) {

            let numberedStepper = new Stepper(horizontalWizard);
            $(horizontalWizard)
                .find('.btn-next')
                .each(function () {
                    $(this).on('click', function (e) {
                        numberedStepper.next();
                    });
                });

            $(horizontalWizard)
                .find('.btn-prev')
                .on('click', function () {
                    numberedStepper.previous();
                });

            $(horizontalWizard)
                .find('.btn-submit')
                .on('click', function () {

                    payment.create(null, null, $('#modal'));
                });
        }


        let registerModal = new bootstrap.Modal($('#registerModal'));
        $('#register').click(function () {
            registerModal.show();
            if (feather) { feather.replace({width : 14, height: 14});}
        });


    }

    function addPremierJob() {

        $('#premier').append(new EJS({url: 'cportalJS/jobcast/jobEJS/premierItem.ejs'}).render());


    }

    return job;
});
