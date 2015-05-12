(function (ng) {
    var mod = ng.module('countryMasterModule');

    mod.controller('countryMasterCtrl', ['$scope', 'countryMasterService', 'countryModel', function ($scope, svc, model) {
            $scope.model = model;
            svc.extendCtrl(this, $scope);
            this.fetchRecords();
        }]);

    mod.controller('ownedSportsCtrl', ['masterUtils', '$scope', 'sportModel', function (masterSvc, $scope, model) {
            $scope.model = model;
            masterSvc.extendChildCtrl(this, $scope, 'ownedSports');
        }]);

    mod.controller('sportsCtrl', ['masterUtils', '$scope', 'sportModel', function (masterSvc, $scope, model) {
            $scope.model = model;
            masterSvc.extendChildCtrl(this, $scope, 'sports');
        }]);
})(window.angular);