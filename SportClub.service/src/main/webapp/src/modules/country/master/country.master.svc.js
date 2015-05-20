(function(ng){
    var mod = ng.module('countryMasterModule');
    
    mod.service('countryMasterService', ['masterUtils', 'countryMasterModule.context', function(utils, ctx){
            utils.extendService(this, ctx);
    }]);
})(window.angular);