(function (ng) {
    var mod = ng.module('sportModule');

    mod.service('sportService', ['CRUDBase', 'sport.context', function (CRUDBase, ctx) {
            this.url = ctx;
            CRUDBase.extendService(this);
        }]);
})(window.angular);
