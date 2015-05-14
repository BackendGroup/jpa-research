(function (ng) {
    var mod = ng.module('countryModule', ['CrudModule']);

    mod.constant('country.context', 'countries');

    mod.constant('country.skipMock', false);

    mod.constant('countryModel', [{
            name: 'name',
            displayName: 'Name',
            type: 'String',
            order: 1
        }, {
            name: 'population',
            displayName: 'Population',
            type: 'Integer',
            order: 2
        }]);
})(window.angular);