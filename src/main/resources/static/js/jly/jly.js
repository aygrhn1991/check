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

});
app.controller('checkpictureCtrl', function ($scope, $http) {

});
app.controller('createidCtrl', function ($scope, $http) {

});