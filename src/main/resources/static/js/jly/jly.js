window.context = '/check';
var app = angular.module('app', ['ngRoute']);
app.config(function ($routeProvider) {
    $routeProvider
        .when('/auth', {
            templateUrl: context + '/jly/auth',
            controller: 'authCtrl'
        })
        .when('/checkpicture', {
            templateUrl: context + '/jly/checkpicture',
            controller: 'checkpictureCtrl'
        })
        .when('/createid', {
            templateUrl: context + '/jly/createid',
            controller: 'createidCtrl'
        })
        .otherwise({
            redirectTo: '/auth'
        });
});
app.controller('authCtrl', function ($scope, $http) {
    $scope.loadData = function () {
        $http.post(context + '/jly/getDevices/' +
            parameterTransfer($scope.searchModel.vehno) + '/' +
            parameterTransfer($scope.searchModel.dtuno) + '/' +
            parameterTransfer($scope.searchModel.simno)).success(function (d) {
            $scope.list = d;
        })
    };
    $scope.unBind = function (e) {
        layer.confirm('确定对车牌号：'+e.aaa+'，终端编号：'+e.bbb+'解除绑定？', function (index) {
            $http.post(context + '/jly/unBind/' + e.aaa).success(function (d) {
                if (d) {
                    alert('解绑成功');
                    $scope.loadData();
                    layer.close(index);
                } else {
                    alert('解绑失败');
                }
            })
        });
    };
    $scope.init = function () {
        $scope.searchModel = {
            vehno: null,
            dtuno: null,
            simno: null
        };
        $scope.loadData();
    };
    $scope.init();
});
app.controller('checkpictureCtrl', function ($scope, $http) {

});
app.controller('createidCtrl', function ($scope, $http) {

});