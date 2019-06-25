window.context = '/check';
var app = angular.module('app',[]);
app.controller('loginCtrl', function ($scope, $http) {
    $scope.authModel = {
        account: null,
        password: null
    };
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
                window.location.href = context + '/home/index';
            } else {
                alert('登陆失败');
            }
        })
    };
    $scope.authModel.account = 'admin';
    $scope.authModel.password = 'admin';
    $scope.auth();
});