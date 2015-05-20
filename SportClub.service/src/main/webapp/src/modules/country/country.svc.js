(function (ng) {
    var mod = ng.module('countryModule');

    mod.service('countryService', ['CRUDBase', 'country.context', function (CRUDBase, ctx) {
            CRUDBase.extendService(this, ctx);
            this.getMostPopulated = function () {
                return this.api.customGET('mostPopulated');
            };
            this.getLeastPopulated = function () {
                return this.api.customGET('leastPopulated');
            };
        }]);
})(window.angular);
