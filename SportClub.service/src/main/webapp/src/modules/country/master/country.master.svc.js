(function(ng){
    var mod = ng.module('countryMasterModule');
    
    mod.service('countryMasterService', ['CRUDBase', 'countryMasterModule.context', function(CRUDBase, ctx){
            this.url = ctx;
            CRUDBase.extendService(this);
    }]);
})(window.angular);