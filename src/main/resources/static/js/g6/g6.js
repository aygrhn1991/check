window.context = '/check';
var app = angular.module('app', ['ngRoute']);
app.config(function ($routeProvider) {
    $routeProvider
        .when('/check', {
            templateUrl: context + '/g6/check',
            controller: 'checkCtl'
        })
        .otherwise({
            redirectTo: '/check'
        });
});
app.controller('checkCtl', function ($scope, $http, $interval) {
    $scope.startCheck = function () {
        if (isNull($scope.dtuType)) {
            alert('请选择检测车辆类型');
            return;
        }
        if (isNull($scope.config.overTime)) {
            alert('请设置超时时间');
            return;
        }
        if (isNull($scope.config.engineSpeed)) {
            alert('请设置发动机转速值');
            return;
        }
        if ($scope.dtuType == 1 && isNull($scope.config.frictionTorque)) {
            alert('请设置摩擦扭矩值');
            return;
        }
        if (!isNumber($scope.config.engineSpeed)) {
            alert('转速格式错误');
            return;
        }
        if (!isNumber($scope.config.overTime)) {
            alert('超时时间格式错误');
            return;
        }
        if ($scope.dtuType == 1 && !isNumber($scope.config.frictionTorque)) {
            alert('摩擦扭矩格式错误');
            return;
        }
        if ($scope.vinError) {
            alert('VIN格式错误');
            return;
        }
        if (isNull($scope.config.vins)) {
            alert('请输入待检VIN(1)');
            return;
        }
        var vinListTemp = $scope.config.vins.split(',');
        $scope.vinList = [];
        vinListTemp.forEach(function (e) {
            if (!isNull(e.trim())) {
                $scope.vinList.push(e.trim());
            }
        });
        if ($scope.vinList.length == 0) {
            alert('请输入待检VIN(2)');
            return;
        }
        layer.msg('启动中！', {time: 1000});
        $http.post(window.context + '/g6/startCheck', {
            overTime: $scope.config.overTime,
            engineSpeed: $scope.config.engineSpeed,
            frictionTorque: $scope.config.frictionTorque,
            vins: $scope.vinList,
            dtuType: $scope.dtuType
        }).success(function (d) {
            console.log('开始检测：' + dateToYYMMDDHHMMSS(new Date($scope.timeStart)));
            $scope.isStart = true;
            $scope.timeStart = new Date().getTime();
            $scope.timeEnd = null;
            $scope.timeSeconds = null;
            $scope.timer = $interval(function () {
                $scope.timeRefresh = dateToYYMMDDHHMMSS(new Date());
                $http.post(window.context + '/g6/onLine').success(function (dd) {
                    dd.forEach(function (ee) {
                        ee.count = ee.datas.length;
                    })
                    $scope.dataList = [];
                    $scope.dataList = dd;
                    console.log(dd);
                })
            }, 2000);
        });
    };
    $scope.stopCheck = function () {
        layer.msg('停止中！', {time: 1000});
        $http.post(window.context + '/g6/stopCheck').success(function (d) {
            console.log('停止检测：' + dateToYYMMDDHHMMSS(new Date($scope.timeEnd)));
            $scope.isStart = false;
            $scope.timeEnd = new Date().getTime();
            $scope.timeSeconds = Math.floor(($scope.timeEnd - $scope.timeStart) / 1000) + 's';
            $scope.timeRefresh = null;
            $interval.cancel($scope.timer);
        })
    };
    $scope.showListModal = function (e) {
        $scope.subDataList = [];
        $scope.subDataList = e.datas;
        $scope.modalIndex = layer.open({
            type: 1,
            title: e.vin + '(' + e.datas.length + '条)',
            shade: 0,
            area: '800px',
            btn: [],
            content: $('#listModal')
        });
    };
    $scope.showPasswordModal = function () {
        $scope.password = null;
        $scope.modalIndex = layer.open({
            type: 1,
            title: '请输入管理员密码',
            shade: 0,
            area: '450px',
            btn: [],
            content: $('#passwordModal')
        });
    };
    $scope.confirmPassword = function () {
        if ($scope.password == '12345678') {
            $scope.configEditable = true;
            layer.close($scope.modalIndex);
        } else {
            alert('管理员密码错误');
        }
    }
    $scope.updateConfig = function () {
        $scope.configEditable = false;
    }
    $scope.vinVerify = function () {
        if ($scope.config.vins.indexOf('，') != -1) {
            $scope.vinError = true;
        } else {
            $scope.vinError = false;
        }
    };
    $scope.init = function () {
        $scope.modalIndex = null;
        $scope.dtuType = null;
        $scope.timeStart = null;
        $scope.timeEnd = null;
        $scope.timeSeconds = null;
        $scope.timeRefresh = null;
        $scope.isStart = false;
        $scope.vinError = false;
        $scope.configEditable = false;
        $scope.password = null;
        $scope.vinList = [];
        $scope.timer = null;
        $scope.dataList = [];
        $scope.subDataList = [];
        $scope.config = {
            vins: null,
            engineSpeed: null,
            frictionTorque: null,
            overTime: null,
        };

        $http.post(window.context + '/g6/getConfigs').success(function (d) {

        })
    };
    $scope.init();
});