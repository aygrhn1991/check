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
        if (isNull($scope.config.overtime)) {
            alert('请设置超时时间');
            return;
        }
        $interval(function () {
            var thisTime = new Date().getTime();
            $scope.dataList.push({
                vin: '001',
                speed: 3000,
                isLocate: true,
                time: thisTime,
                isOverTime: (new Date( thisTime).getSeconds() -new Date( $scope.lastTime).getSeconds()) > 1 ? true : false
            });
            $scope.lastTime=thisTime;
        }, 1000);
    };
    $scope.saveConfig = function () {
        alert('已经保存并生效');
    }
    $scope.dataList = [];
    $scope.init = function () {
        $scope.lastTime = 0;
        $scope.dataList = [];
        $scope.config = {
            speed: null,
            overtime: null,
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