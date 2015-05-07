(function (ng) {
    var countryModule = ng.module('countryModule');

    countryModule.controller('countryCtrl', ['$scope', 'countryService', 'countryModel', function ($scope, svc, model) {
            $scope.model = model;
            svc.extendCtrl(this, $scope);
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
            var self = this;
            this.globalActions.push({
                name: 'leastPopulated',
                displayName: 'Least Populated',
                icon: 'star',
                fn: function () {
                    self.getLeastPopulated();
                },
                show: function () {
                    return true;
                }
            });
            this.globalActions.push({
                name: 'mostPopulated',
                displayName: 'Most Populated',
                icon: 'star',
                fn: function () {
                    self.getMostPopulated();
                },
                show: function () {
                    return true;
                }
            });
        }]);
})(window.angular);