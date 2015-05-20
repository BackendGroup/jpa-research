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
    mod.controller('sportsCtrl', ['masterUtils', '$scope', 'sportModel', 'sportService', '$modal', function (masterSvc, $scope, model, svc, $modal) {
            masterSvc.extendCompChildCtrl(this, $scope, model, 'sports', "country");
            var self = this;
            this.showList = function () {
                var modal = $modal.open({
                    animation: true,
                    templateUrl: 'myModalContent.html',
                    controller: 'modalCtrl',
                    resolve: {
                        items: function(){
                            return svc.fetchRecords();
                        }
                    }
                });
            };
            this.globalActions = [{
                    name: 'select',
                    displayName: 'Select',
                    icon: 'check',
                    fn: function () {
                        self.showList();
                    },
                    show: function () {
                        return !self.editMode;
                    }
                }];
        }]);
    mod.controller('modalCtrl', ['$scope', '$modalInstance', 'items', function ($scope, $modalInstance, items) {
            $scope.items = items;

            $scope.ok = function () {
                $modalInstance.close($scope.selected.item);
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
})(window.angular);