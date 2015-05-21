(function (ng) {
    var mod = ng.module('CrudModule');

    mod.controller('modalCtrl', ['$scope', '$modalInstance', 'items', 'name', function ($scope, $modalInstance, items, name) {
            $scope.name = name;
            $scope.items = items;

            function getSelectedItems() {
                return $scope.items.filter(function(item){return !!item.selected;});
            }

            $scope.ok = function () {
                $modalInstance.close(getSelectedItems());
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
})(window.angular);