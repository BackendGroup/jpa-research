(function (ng) {
    var mod = ng.module('countryMasterModule');

    mod.controller('countryMasterCtrl', ['$scope', 'countryMasterService', 'countryModel', function ($scope, svc, model) {
            $scope.model = model;
            svc.extendCtrl(this, $scope);
            this.fetchRecords();
        }]);
    
    mod.controller('sportsCtrl', ['masterUtils', '$scope', function(masterSvc, $scope){
            masterSvc.extendService(this, $scope, 'sports');
    }]);
})(window.angular);