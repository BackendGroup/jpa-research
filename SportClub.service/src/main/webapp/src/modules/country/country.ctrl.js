(function (ng) {
    var countryModule = ng.module('countryModule');

    countryModule.controller('countryCtrl', ['$scope', 'countryService', 'countryModel', function ($scope, svc, model) {
            $scope.model = model;
            svc.extendCtrl(this, $scope);
            var self = this;
            this.globalActions = [{
                    name: 'create',
                    displayName: 'Create',
                    fn: self.createRecord,
                    context: self,
                    show: !self.editMode,
                    icon: 'plus'
                }
            ];
            this.fetchRecords();
            this.getMostPopulated = function () {
                svc.getMostPopulated().then(function (data) {
                    alert('The most populated country is ' + data.name + ' with ' + data.population + ' habitants');
                }, function () {
                    alert('There are no countries with population on server');
                });
            };

            this.getLeastPopulated = function () {
                svc.getLeastPopulated().then(function (data) {
                    alert('The least populated country is ' + data.name + ' with ' + data.population + ' habitants');
                }, function () {
                    alert('There are no countries with population on server');
                });
            };
        }]);
})(window.angular);