(function (ng) {
    var countryModule = ng.module('countryModule');

    countryModule.controller('countryCtrl', ['$scope', 'countryService', 'countryModel', function ($scope, svc, model) {
            svc.extendController(this, $scope, model);
            this.fetchRecords();
            var self = this;
            this.getMostPopulated = function () {
                svc.getMostPopulated().then(function (data) {
                    self.showSuccess('The most populated country is ' + data.name + ' with ' + data.population + ' habitants');
                }, function () {
                    self.showError('There are no countries with population on server');
                });
            };

            this.getLeastPopulated = function () {
                svc.getLeastPopulated().then(function (data) {
                    self.showSuccess('The least populated country is ' + data.name + ' with ' + data.population + ' habitants');
                }, function () {
                    self.showError('There are no countries with population on server');
                });
            };
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
            }, {
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