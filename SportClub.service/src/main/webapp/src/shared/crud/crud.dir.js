(function (ng) {
    var crudModule = ng.module('CrudModule');

    crudModule.directive('listRecords', [function () {
            return {
                scope: {
                    records: '=*',
                    model: '=',
                    totalItems: '=',
                    currentPage: '=',
                    maxSize: '=',
                    itemsPerPage: '=',
                    editMode: '=',
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
})(window.angular);