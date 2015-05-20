(function (ng) {
    var mod = ng.module('CrudModule');

    mod.directive('listRecords', [function () {
            return {
                scope: {
                    records: '=*',
                    model: '=*',
                    actions: '=*?'
                },
                restrict: 'E',
                templateUrl: 'src/shared/crud/list.tpl.html'
            };
        }]);

    mod.directive('toolbar', [function () {
            return {
                scope: {
                    actions: '=*',
                    name: '@',
                    displayName: '@'
                },
                restrict: 'E',
                templateUrl: 'src/shared/crud/toolbar.tpl.html'
            };
        }]);
})(window.angular);