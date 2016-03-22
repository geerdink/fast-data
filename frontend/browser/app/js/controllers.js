/**
 * Created by GK46IV on 3/8/2016.
 */
angular.module('NameListApp.controllers', []).
controller('nameController', function($scope, restAPIservice) {
    //$scope.nameFilter = null;
    $scope.nameList = [];
    $scope.offerList = [];

    restAPIservice.getNames().success(function (response) {
        //Dig into the responde to get the relevant data
        $scope.nameList = response;
        //$scope.searchFilter = function (name) {
        //    var keyword = new RegExp($scope.nameFilter, 'i');
        //    return !$scope.nameFilter || keyword.test(name.firstname) || keyword.test(name.lastname);
        //};
    });

    restAPIservice.getMe("it is I").success(function (response) {
        //Dig into the responde to get the relevant data
        $scope.offerList = response;
        //$scope.searchFilter = function (name) {
        //    var keyword = new RegExp($scope.nameFilter, 'i');
        //    return !$scope.nameFilter || keyword.test(name.firstname) || keyword.test(name.lastname);
        //};
    });
});

