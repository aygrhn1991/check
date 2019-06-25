window.dateToYYMMDDHHMMSS = function (date) {
    var M = ((date.getMonth() + 1) >= 10 ? '' : '0') + (date.getMonth() + 1);
    var d = (date.getDate() >= 10 ? '' : '0') + date.getDate();
    var h = (date.getHours() >= 10 ? '' : '0') + date.getHours();
    var m = (date.getMinutes() >= 10 ? '' : '0') + date.getMinutes();
    var s = (date.getSeconds() >= 10 ? '' : '0') + date.getSeconds();
    return date.getFullYear() + '/' + M + '/' + d + ' ' + h + ':' + m + ':' + s;
};
window.dateToYYMMDD = function (date) {
    var M = ((date.getMonth() + 1) >= 10 ? '' : '0') + (date.getMonth() + 1);
    var d = (date.getDate() >= 10 ? '' : '0') + date.getDate();
    return date.getFullYear() + '/' + M + '/' + d;
};
window.dateToYYMMDD_ = function (date) {
    var M = ((date.getMonth() + 1) >= 10 ? '' : '0') + (date.getMonth() + 1);
    var d = (date.getDate() >= 10 ? '' : '0') + date.getDate();
    return date.getFullYear() + '-' + M + '-' + d;
};
window.getRandomNum = function (min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min);
};
window.isNull = function (obj) {
    return (obj === undefined || obj === null || obj === '') ? true : false;
};
window.isNumber = function (obj) {
    var reg = /^[0-9]*$/;
    return reg.test(obj);
};
window.parameterTransfer = function (value) {
    if (isNull(value)) {
        return -1;
    } else {
        return value;
    }
};