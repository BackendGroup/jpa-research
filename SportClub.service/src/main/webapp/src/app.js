(function (ng) {

    var mainApp = ng.module('mainApp', ['ngRoute', 'sportModule', 'countryModule', 'countryMasterModule']);

    mainApp.config(['$routeProvider', function ($routeProvider) {
            var crudTpl = 'src/shared/crud/crud.tpl.html';
            var crudCtrl = 'ctrl';
            $routeProvider.when('/sport', {
                templateUrl: 'src/modules/sport/sport.tpl.html',
                controller: 'sportCtrl',
                controllerAs: crudCtrl
            }).when('/country', {
                templateUrl: crudTpl,
                controller: 'countryCtrl',
                controllerAs: crudCtrl
            }).when('/country/master', {
                templateUrl: 'src/modules/country/master/country.master.tpl.html',
                controller: 'countryMasterCtrl',
                controllerAs: crudCtrl
            }).otherwise('/');
        }]);
})(window.angular);