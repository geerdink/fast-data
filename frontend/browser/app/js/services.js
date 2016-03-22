/**
 * Created by GK46IV on 3/8/2016.
 */
angular.module('NameListApp.services', []).
factory('restAPIservice', function($http) {

    var restAPI = {};

    restAPI.getNames = function() {
        return $http({
            //method: 'JSONP',
            //url: 'http://ergast.com/api/f1/2013/driverStandings.json?callback=JSON_CALLBACK'
            //url: 'http://localhost:8080/customer?callback=JSON_CALLBACK'
            url: 'http://localhost:8080/customer/'
        });
    }

    restAPI.getMe = function(theName) {
        return $http({
            //method: 'JSONP',
            //url: 'http://ergast.com/api/f1/2013/driverStandings.json?callback=JSON_CALLBACK'
            //url: 'http://localhost:8080/customer?callback=JSON_CALLBACK'
            url: 'http://172.16.33.16:8085/hello/' + theName
        });
    }

    return restAPI;
});