/**
 * Created by GK46IV on 3/8/2016.
 */
angular.module('NameListApp.services', []).
factory('restAPIservice', function($http) {

    var restAPI = {};

    restAPI.getDrivers = function() {
        return $http({
            //method: 'JSONP',
            //url: 'http://ergast.com/api/f1/2013/driverStandings.json?callback=JSON_CALLBACK'
            //url: 'http://localhost:8080/customer?callback=JSON_CALLBACK'
            url: 'http://localhost:8080/customer'
        });
    }

    return restAPI;
});