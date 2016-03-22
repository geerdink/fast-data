/**
 * Created by GK46IV on 3/8/2016.
 */
angular.module('NameListApp.controllers', []).
controller('driversController', function($scope, restAPIservice) {
    $scope.nameFilter = null;
    $scope.nameList = [];

    restAPIservice.getDrivers().success(function (response) {
        //Dig into the responde to get the relevant data
        $scope.nameList = response;
        $scope.searchFilter = function (name) {
            var keyword = new RegExp($scope.nameFilter, 'i');
            return !$scope.nameFilter || keyword.test(name.firstname) || keyword.test(name.lastname);
        };
    });
});

