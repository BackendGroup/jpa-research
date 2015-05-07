(function (ng) {
    var mod = ng.module('CrudModule');

    mod.directive('listRecords', [function () {
            return {
                scope: {
                    records: '=*',
                    model: '=',
                    totalItems: '=',
                    currentPage: '=',
                    maxSize: '=',
                    itemsPerPage: '=',
                    changePage: '&',
                    editRecord: '&',
                    deleteRecord: '&'
                },
                restrict: 'E',
                templateUrl: 'src/shared/crud/list.tpl.html',
                link: function (scope, el, attr) {
                    scope.$watch(attr.currentPage, function () {
                        scope.changePage();
                    });
                }
            };
        }]);
    
    mod.directive('toolbar', [function(){
            return {
                scope: {
                    actions: '=*',
                    name: '@',
                    displayName: '@'
                },
                restrict: 'E',
                templateUrl: 'src/shared/crud/menu.tpl.html'
            };
    }]);
})(window.angular);