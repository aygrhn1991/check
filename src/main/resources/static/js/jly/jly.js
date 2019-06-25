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
    $scope.loadData = function (reset) {
        if (reset) {
            $scope.pageModel.pageIndex = 1;
        }
        $http.post(context + '/jly/getDevices/' +
            $scope.pageModel.pageIndex + '/' +
            $scope.pageModel.pageSize + '/' +
            parameterTransfer($scope.searchModel.vehNo) + '/' +
            parameterTransfer($scope.searchModel.dtuNo) + '/' +
            parameterTransfer($scope.searchModel.simNo)).success(function (d) {
            $scope.list = d.data;
            if (reset) {
                $scope.makePage($scope.pageModel.pageSize, d.count);
            }
        })
    };
    $scope.makePage = function (pageSize, total) {
        layui.laypage.render({
            elem: 'page',
            limit: pageSize,
            count: total,
            jump: function (obj, first) {
                if (!first) {
                    $scope.pageModel.pageIndex = obj.curr;
                    $scope.loadData(false);
                }
            }
        });
    };
    $scope.unBind = function (e) {
        layer.confirm('确定对车牌号：' + e.aaa + '，终端编号：' + e.bbb + '解除绑定？', function (index) {
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
        $scope.pageModel = {
            pageIndex: 1,
            pageSize: 10
        };
        $scope.searchModel = {
            vehNo: null,
            dtuNo: null,
            simNo: null
        };
        $scope.loadData(true);
    };
    $scope.init();
});
app.controller('checkpictureCtrl', function ($scope, $http, $interval) {
    $scope.getOnlineDevices = function () {
        $http.post(context + '/jly/getOnlineDevices').success(function (d) {
            $scope.onlineDeviceList = d.data;
        });
    };
    $scope.takePicture = function () {
        if (isNull($scope.searchModel.dtuNo)) {
            alert('请选择拍照设备');
            return;
        }
        $http.post(context + '/jly/takePicture/' + $scope.searchModel.dtuNo).success(function (d) {
            if (d) {
                alert('拍照指令发送成功，等待照片数据上传');
                $scope.timer = $interval(function () {
                    $http.post(context + '/jly/getPictureData/' + $scope.searchModel.dtuNo).success(function (d) {
                        $scope.list = [];
                        $scope.list = d;
                    })
                }, 2000);
            } else {
                alert('拍照指令发送失败');
            }
        })
    };
    $scope.stop = function () {
        $interval.cancel($scope.timer);
    };
    $scope.init = function () {
        $scope.searchModel = {
            dtuNo: null
        };
        $scope.getOnlineDevices();
    };
    $scope.init();
});
app.controller('createidCtrl', function ($scope, $http) {
    $scope.getOnlineDevices = function () {
        $http.post(context + '/jly/getOnlineDevices').success(function (d) {
            $scope.onlineDeviceList = d.data;
        });
    };
    $scope.takePicture = function () {
        if (isNull($scope.searchModel.dtuNo)) {
            alert('请选择拍照设备');
            return;
        }
        $http.post(context + '/jly/takePicture/' + $scope.searchModel.dtuNo).success(function (d) {
            if (d) {
                alert('拍照指令发送成功，等待照片数据上传');
                $scope.timer = $interval(function () {
                    $http.post(context + '/jly/getPictureData/' + $scope.searchModel.dtuNo).success(function (d) {
                        $scope.list = [];
                        $scope.list = d;
                    })
                }, 2000);
            } else {
                alert('拍照指令发送失败');
            }
        })
    };
    $scope.stop = function () {
        console.log($scope.pageModel.date);
    };
    $scope.init = function () {
        $scope.pageModel = {
            date: dateToYYMMDD_(new Date())
        };
        $scope.getOnlineDevices();
    };
    $scope.init();
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#date',
            done: function (value, date, endDate) {
                $scope.pageModel.date = dateToYYMMDD_(new Date(value));
            }
        });
    });
});