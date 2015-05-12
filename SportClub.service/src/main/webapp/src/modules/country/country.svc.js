(function (ng) {
    var mod = ng.module('countryModule');

    mod.service('countryService', ['CRUDBase', 'country.context', function (CRUDBase, ctx) {
            this.url = ctx;
            CRUDBase.extendService(this);
            this.getMostPopulated = function () {
                return this.api.customGET('mostPopulated');
            };
            this.getLeastPopulated = function () {
                return this.api.customGET('leastPopulated');
            };
        }]);
})(window.angular);
