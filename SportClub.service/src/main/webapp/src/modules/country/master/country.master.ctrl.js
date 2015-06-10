(function (ng) {
    var mod = ng.module('countryMasterModule');
    mod.controller('countryMasterCtrl', ['$scope', 'countryMasterService', 'countryModel', function ($scope, svc, model) {
            svc.extendController(this, $scope, model, 'countryMaster', 'Country Master');
            this.fetchRecords();
        }]);
    mod.controller('ownedSportsCtrl', ['masterUtils', '$scope', 'sportModel', function (masterSvc, $scope, model) {
            masterSvc.extendCompChildCtrl(this, $scope, model, 'ownedSports', "country");
        }]);
    mod.controller('sportsCtrl', ['masterUtils', '$scope', 'sportModel', 'sportService', function (masterSvc, $scope, model, svc) {
            masterSvc.extendAggChildCtrl(this, $scope, model, 'sports', svc);
        }]);

})(window.angular);