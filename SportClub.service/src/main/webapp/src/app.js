(function (ng) {

    var mainApp = ng.module('mainApp', ['ngRoute', 'sportModule', 'countryModule', 'countryMasterModule']);

    mainApp.config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/sport', {
                templateUrl: 'src/modules/sport/sport.tpl.html'
            }).when('/country', {
                templateUrl: 'src/modules/country/country.tpl.html',
                controller: 'countryCtrl',
                controllerAs: 'countryCtrl'
            }).when('/country/master', {
                templateUrl: 'src/modules/country/master/country.master.tpl.html',
                controller: 'countryMasterCtrl',
                controllerAs: 'countryCtrl'
            }).otherwise('/');
        }]);
})(window.angular);