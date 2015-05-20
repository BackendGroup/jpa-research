(function (ng) {
    var mod = ng.module('CrudModule');

    mod.controller('modalCtrl', ['$scope', '$modalInstance', 'items', 'name', function ($scope, $modalInstance, items, name) {
            $scope.name = name;
            $scope.items = items;

            $scope.ok = function () {
                $modalInstance.close($scope.items);
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
})(window.angular);