(function (ng) {
    var countryMaster = ng.module('countryMasterModule', ['countryModule', 'sportModule']);

    countryMaster.controller('countryMasterCtrl', ['$scope', function ($scope) {
            $scope.tab = 'sports';
            this.changeTab = function (tabName) {
                $scope.tab = tabName;
            };

            this.editMode = false;

            this.createRecord = function () {
                this.editMode = true;
            };
            
            this.fetchRecords = function(){
                this.editMode = false;
            };
        }]);
})(window.angular);