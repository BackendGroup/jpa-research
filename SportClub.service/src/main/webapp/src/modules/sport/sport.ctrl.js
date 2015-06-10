(function (angular) {
    var sportModule = angular.module('sportModule');

    sportModule.controller('sportCtrl', ['$scope', 'sportService', 'sportModel', function ($scope, sportSvc, model) {
            sportSvc.extendController(this, $scope, model, 'sport', 'Sport');
            this.loadRefOptions();
            this.fetchRecords();
        }]);
})(window.angular);