(function (ng) {
    var mod = ng.module('countryModule', ['CrudModule']);

    mod.constant('country.context', 'countries');

    mod.constant('country.skipMock', false);

    mod.constant('countryModel', [{
            name: 'name',
            displayName: 'Name',
            type: 'String',
            required: true
        }, {
            name: 'population',
            displayName: 'Population',
            type: 'Integer',
            required: true
        }]);
})(window.angular);