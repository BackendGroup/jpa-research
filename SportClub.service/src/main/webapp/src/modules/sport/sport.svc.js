(function (ng) {
    var mod = ng.module('sportModule');

    mod.service('sportService', ['CRUDBase', 'sport.context', function (CRUDBase, ctx) {
            CRUDBase.extendService(this, ctx);
        }]);
})(window.angular);
