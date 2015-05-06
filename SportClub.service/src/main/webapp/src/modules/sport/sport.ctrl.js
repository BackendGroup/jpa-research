(function (angular) {
    var sportModule = angular.module('sportModule');

    sportModule.controller('sportCtrl', ['$scope', 'sportService','countryService', 'sportModel', function ($scope, sportSvc, countrySvc, model) {
            
            $scope.model = model;
            sportSvc.extendCtrl(this, $scope);
            this.fetchRecords();
            this.getAvgAge = function (sport) {
                return (sport.minAge + sport.maxAge) / 2;
            };
            var getCountryName = function(record){
                for (var i in $scope.countryRecords) {
                    if ($scope.countryRecords[i].id === record.id) {
                        return $scope.countryRecords[i].name;
                    }
                }
                return;
            };
            countrySvc.fetchRecords().then(function(data){
                $scope.countryRecords = data;
                model.country.compute = getCountryName;
            });
        }]);
})(window.angular);