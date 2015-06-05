(function (ng) {

    var mainApp = ng.module('mainApp', ['ngRoute', 'sportModule', 'countryModule', 'countryMasterModule']);

    mainApp.config(['$routeProvider', function ($routeProvider) {
            var crudTpl = 'src/shared/crud/crud.tpl.html';
            $routeProvider.when('/sport', {
                templateUrl: crudTpl,
                controller: 'sportCtrl',
                controllerAs: 'ctrl'
            }).when('/country', {
                templateUrl: crudTpl,
                controller: 'countryCtrl',
                controllerAs: 'ctrl'
            }).when('/country/master', {
                templateUrl: 'src/modules/country/master/country.master.tpl.html',
                controller: 'countryMasterCtrl',
                controllerAs: 'ctrl'
            }).otherwise('/');
        }]);
})(window.angular);