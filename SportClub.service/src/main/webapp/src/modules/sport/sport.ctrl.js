(function (angular) {
    var sportModule = angular.module('sportModule');

    sportModule.controller('sportCtrl', ['$scope', 'sportService', 'sportModel', function ($scope, sportSvc, model) {
            sportSvc.extendController(this, $scope, model, 'sport', 'Sport');
            $scope.showSearchFields = true;
            $scope.searchParams = {};

            var self = this;

            this.searchByName = function () {
                return sportSvc.searchByName($scope.searchParams.name).then(function (data) {
                    $scope.records = data;
                    self.totalItems = 1;
                    $scope.currentRecord = {};
                    self.editMode = false;
                    return data;
                });
            };

            this.globalActions.push({
                name: 'search',
                displayName: 'Search',
                icon: 'search',
                show: function () {
                    return true;
                },
                fn: function () {
                    return self.searchByName();
                }
            });

            this.loadRefOptions();
            this.fetchRecords();
        }]);
})(window.angular);