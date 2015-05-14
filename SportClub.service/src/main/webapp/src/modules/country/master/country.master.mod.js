(function (ng) {
    var mod = ng.module('countryMasterModule', ['countryModule', 'sportModule', 'masterModule']);

    mod.constant('countryMasterModule.context', 'countries/master');
    
    mod.constant('countryMasterModule.skipMock', true);

    mod.run(['countryMasterModule.skipMock', 'countryMasterModule.context', 'MockModule.service', function (skip, ctx, svc) {
            svc.setMock(ctx, skip);
        }]);
})(window.angular);