define([], function () {

    let payment = {};

    payment.create = function (pay, object, element) {
        element.append(new EJS({url: 'cportalJS/common/payment/ejs/paymentModal.ejs'}).render());

        let registerModal = new bootstrap.Modal($('#paymentModal'));
        registerModal.show();
    }

    

    return payment;
});
