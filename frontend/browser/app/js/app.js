/**
 * Created by GK46IV on 3/8/2016.
 */
var NameListApp = angular.module('NameListApp', [
    'NameListApp.controllers',
    'NameListApp.services',
    'ngRoute'
]);

NameListApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'app/main.html',
                controller: 'nameController'
            })
            .when('/:param', {
                templateUrl: 'app/main.html',
                controller: 'nameController'
            })

            .when('/app', {
                templateUrl: 'app/main.html',
                controller: 'nameController'
            })
            .when('/app/:param', {
                templateUrl: 'app/main.html',
                controller: 'nameController'
            });



    //.when(/)


}]);