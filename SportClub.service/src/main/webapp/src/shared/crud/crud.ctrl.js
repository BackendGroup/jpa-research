(function (ng) {
    var mod = ng.module('CrudModule');

    mod.controller('modalCtrl', ['$scope', '$modalInstance', 'items', 'name', function ($scope, $modalInstance, items, name) {
            $scope.name = name;
            $scope.items = items;

            function isSelected(item) {
                return item.selected;
            }

            $scope.ok = function () {
                $modalInstance.close($scope.items.filter(isSelected));
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
})(window.angular);