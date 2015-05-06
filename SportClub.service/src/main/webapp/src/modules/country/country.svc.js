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

    mod.factory('countryModel', [function () {
        return [{
                name: 'name',
                displayName: 'Name',
                type: 'String',
                order: 1
            }, {
                name: 'population',
                displayName: 'Population',
                type: 'Integer',
                order: 2
            }];
    }]);
})(window.angular);
