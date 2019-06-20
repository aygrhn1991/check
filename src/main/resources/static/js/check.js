window.context = '/check';
var app = angular.module('app', ['ngRoute']);
app.config(function ($routeProvider) {
    $routeProvider
        .when('/check', {
            templateUrl: context + '/admin/check',
            controller: 'checkCtl'
        })
        .otherwise({
            redirectTo: '/check'
        });
});
app.controller('checkCtl', function ($scope, $http, $interval) {
    $scope.startCheck = function () {
        if (isNull($scope.config.speed)) {
            alert('请设置对比转速值');
            return;
        }
        if (isNull($scope.config.overTime)) {
            alert('请设置超时时间');
            return;
        }
        if ($scope.vinError) {
            alert('VIN格式错误');
            return;
        }
        if (isNull($scope.config.vins)) {
            alert('请输入待检VIN');
            return;
        }
        var vinListTemp = $scope.config.vins.split(',');
        console.log(vinListTemp);
        $scope.vinList = [];
        vinListTemp.forEach(function (e) {
            if (!isNull(e.trim())) {
                $scope.vinList.push(e.trim());
            }
        });
        console.log($scope.vinList);
        if ($scope.vinList.length == 0) {
            alert('请输入待检VIN');
            return;
        }
        $http.post(window.context + '/admin/startCheck', {
            speed: $scope.config.speed,
            overTime: $scope.config.overTime,
            vins: $scope.vinList,
        }).success(function (d) {
            console.log(d);
            $scope.timer = $interval(function () {
                $http.post(window.context + '/admin/onLine').success(function (dd) {
                    dd.forEach(function (ee) {
                        ee.count = ee.datas.length;
                    })
                    $scope.dataList = [];
                    $scope.dataList = dd;
                    console.log(dd);
                })
            }, 3000);
        });
    };
    $scope.stopCheck = function () {
        $http.post(window.context + '/admin/stopCheck').success(function (d) {
            $interval.cancel($scope.timer);
            console.log(d);
        })
    };
    $scope.showList = function (e) {
        $scope.subDataList = [];
        $scope.subDataList = e.datas;
        layer.open({
            type: 1,
            shade: 0,
            area: '800px',
            maxHeight: '400px',
            btn: ['关闭'],
            content: $('#listModal'),
            yes: function (index, layero) {
                layer.close(index);
            }
        });
    };
    $scope.vinVerify = function () {
        console.log($scope.config.vins.indexOf('，'))
        if ($scope.config.vins.indexOf('，') != -1) {
            $scope.vinError = true;
        } else {
            $scope.vinError = false;
        }
    };
    $scope.dataList = [];
    $scope.init = function () {
        $scope.vinError = false;
        $scope.vinList = [];
        $scope.timer = null;
        $scope.dataList = [];
        $scope.subDataList = [];
        $scope.config = {
            vins: 'LBTEST00000001106,\n' +
                'LBTEST00000001107,\n' +
                'LBTEST00000001108,\n' +
                'LBTEST00000001109,',
            speed: 3000,
            overTime: 10,
        };
    };
    $scope.init();
});
app.controller('loginCtrl', function ($scope, $http) {
    $scope.authModel = {
        account: null,
        password: null
    }
    $scope.auth = function () {
        if ($scope.authModel.account === null ||
            $scope.authModel.account === '' ||
            $scope.authModel.password === null ||
            $scope.authModel.password === '') {
            alert('请填写账号密码');
            return;
        }
        $http.post(context + '/auth/auth?account=' + $scope.authModel.account + '&password=' + $scope.authModel.password).success(function (d) {
            if (d) {
                window.location.href = context + '/admin/index';
            } else {
                alert('登陆失败');
            }
        })
    };
    $scope.authModel.account = 'admin';
    $scope.authModel.password = 'admin';
    $scope.auth();
});