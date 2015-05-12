(function(ng){
    var mod = ng.module('countryMasterModule');
    
    mod.service('countryMasterService', ['masterUtils', 'countryMasterModule.context', function(utils, ctx){
            this.url = ctx;
            utils.extendService(this);
    }]);
})(window.angular);