define([], function () {

    let utils = {};

    utils.isEmpty = function (value) {
        return value === "" || value === null || value === undefined || (typeof value === "object" && !Object.keys(value).length);

    }


    return utils;
});
