(function (ng) {
    var mod = ng.module('CrudModule');

    mod.controller('listCtrl', ['$scope', function ($scope) {
            $scope.checkAll = function () {
                this.records.forEach(function (item) {
                    item.selected = !item.selected;
                });
            };
        }]);

    mod.controller('datePickerCtrl', ['$scope', function ($scope) {
            $scope.today = function () {
                $scope.value = new Date();
            };

            $scope.clear = function () {
                $scope.value = null;
            };

            $scope.open = function ($event) {
                $event.preventDefault();
                $event.stopPropagation();

                $scope.opened = true;
            };
        }]);

    mod.controller('modalCtrl', ['$scope', '$modalInstance', 'items', 'name', function ($scope, $modalInstance, items, name) {
            $scope.model = [{name: 'name', displayName: 'Name', type: 'String', order: 1}];
            $scope.name = name;
            $scope.items = items;

            function getSelectedItems() {
                return $scope.items.filter(function (item) {
                    return !!item.selected;
                });
            }

            $scope.ok = function () {
                $modalInstance.close(getSelectedItems());
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
})(window.angular);