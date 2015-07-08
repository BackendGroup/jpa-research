(function (ng) {
    var mod = ng.module('sportModule');

    mod.service('sportService', ['CRUDBase', 'sport.context', function (CRUDBase, ctx) {
            CRUDBase.extendService(this, ctx);
            
            this.searchByName = function(name){
                return this.api.getList({name: name});
            };
        }]);
})(window.angular);
