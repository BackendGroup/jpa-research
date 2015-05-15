(function (ng) {
    var mod = ng.module('countryMasterModule', ['countryModule', 'sportModule', 'masterModule']);

    mod.constant('countryMasterModule.context', 'countries/master');
})(window.angular);