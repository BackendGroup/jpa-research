(function (ng) {
    var mod = ng.module('CrudModule');

    mod.controller('modalCtrl', ['$scope', '$modalInstance', 'items', function ($scope, $modalInstance, items) {
            $scope.items = items;

            $scope.ok = function () {
                $modalInstance.close($scope.items);
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
})(window.angular);