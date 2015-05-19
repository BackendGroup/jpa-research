(function (ng) {
    var mod = ng.module('countryMasterModule');

    mod.controller('countryMasterCtrl', ['$scope', 'countryMasterService', 'countryModel', function ($scope, svc, model) {
            $scope.model = model;
            svc.extendCtrl(this, $scope);
            this.fetchRecords();
        }]);

    mod.controller('ownedSportsCtrl', ['masterUtils', '$scope', 'sportModel', function (masterSvc, $scope, model) {
            masterSvc.extendCompChildCtrl(this, $scope, model, 'ownedSports', "country");
        }]);

    mod.controller('sportsCtrl', ['masterUtils', '$scope', 'sportModel', 'sportService', function (masterSvc, $scope, model, svc) {
            masterSvc.extendCompChildCtrl(this, $scope, model, 'sports', "country");
            var self = this;
            this.showList = function(){
                
            };
            this.globalActions = [{
                    name: 'select',
                    displayName: 'Select',
                    icon: 'check',
                    fn: function () {
                        self.createRecord();
                    },
                    show: function () {
                        return !self.editMode;
                    }
                }];
        }]);
})(window.angular);