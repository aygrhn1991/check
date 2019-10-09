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
        var vinListTemp = $scope.vinStr.split(',');
        $scope.vinList = [];
        vinListTemp.forEach(function (e) {
            if (!isNull(e.trim())) {
                $scope.vinList.push(e.trim());
            }
        });
        layer.msg('启动中！', {time: 1000});
        $http.post(window.context + '/g6/startCheck', {
            inTime: $scope.config.inTime,
            engineSpeed: $scope.config.engineSpeed,
            tankLevel: $scope.config.tankLevel,
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
            title: e.vin + '(' + e.iccid + ')' + '(' + e.datas.length + '条)',
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
        var vinListTemp = $scope.vinStr.split(',');
        $scope.vinList = [];
        vinListTemp.forEach(function (e) {
            if (!isNull(e.trim())) {
                $scope.vinList.push(e.trim());
            }
        });
        $http.post(window.context + '/g6/saveConfig',
            Object.assign($scope.config, {dtuType: $scope.dtuType, vins: $scope.vinList})).success(function (d) {
            if (d == true) {
                alert('保存成功');
                $scope.configEditable = false;
            }
        })
    }
    $scope.getConfig = function () {
        $http.post(window.context + '/g6/getConfigs').success(function (d) {
            $scope.configs = d;
            $scope.vinStr = d.vins;
        })
    }
    $scope.setConfig = function (e) {
        $scope.dtuType = e;
        if ($scope.dtuType == 0) {
            $scope.config = $scope.configs.j6;
        }
        if ($scope.dtuType == 1) {
            $scope.config = $scope.configs.j6_gas;
        }
        if ($scope.dtuType == 2) {
            $scope.config = $scope.configs.j7;
        }
    }
    $scope.vinVerify = function () {
        if ($scope.vinStr.indexOf('，') != -1) {
            $scope.vinError = true;
        } else {
            $scope.vinError = false;
        }
    };
    $scope.init = function () {
        $scope.configs = null;//从rest加载的配置项
        $scope.modalIndex = null;//modal索引，关闭时要传此作为参数
        $scope.dtuType = null;//终端类型
        $scope.timeStart = null;//开始时间
        $scope.timeEnd = null;//结束时间
        $scope.timeSeconds = null;//检测计时
        $scope.timeRefresh = null;//数据刷新时间
        $scope.isStart = false;//是否开始的标志
        $scope.vinError = false;//vin格式错误警告
        $scope.configEditable = false;//配置项的可编辑状态
        $scope.password = null;//密码
        $scope.vinList = [];
        $scope.vinStr = 'LBTEST00000001007';
        $scope.timer = null;
        $scope.dataList = [];
        $scope.subDataList = [];
        $scope.config = {
            inTime: null,
            engineSpeed: null,
            tankLevel: null,
            frictionTorque: null,
        };
        $scope.getConfig();
    };
    $scope.init();
});