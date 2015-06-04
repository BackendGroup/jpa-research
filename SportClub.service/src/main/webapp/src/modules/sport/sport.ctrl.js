(function (angular) {
    var sportModule = angular.module('sportModule');

    sportModule.controller('sportCtrl', ['$scope', 'sportService','countryService', 'sportModel', function ($scope, sportSvc, countrySvc, model) {
            sportSvc.extendController(this, $scope, model);
            this.fetchRecords();
            
            var self = this;
            countrySvc.fetchRecords().then(function(data){
                self.setModelOptions('country', data.plain());
            });
        }]);
})(window.angular);