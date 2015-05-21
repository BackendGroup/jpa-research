(function (angular) {
    var sportModule = angular.module('sportModule');

    sportModule.controller('sportCtrl', ['$scope', 'sportService','countryService', 'sportModel', function ($scope, sportSvc, countrySvc, model) {
            sportSvc.extendController(this, $scope, model);
            this.fetchRecords();
            this.getAvgAge = function (sport) {
                return (sport.minAge + sport.maxAge) / 2;
            };
            var getCountryName = function(record){
                for (var i in $scope.countryRecords) {
                    if ($scope.countryRecords[i].id === record.country) {
                        return $scope.countryRecords[i].name;
                    }
                }
                return;
            };
            countrySvc.fetchRecords().then(function(data){
                $scope.countryRecords = data.plain();
                for(var i in model){
                    if(model.hasOwnProperty(i) && model[i].name === 'country'){
                        model[i].compute = getCountryName;
                        return;
                    }
                }
            });
        }]);
})(window.angular);