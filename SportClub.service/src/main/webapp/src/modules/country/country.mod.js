(function (ng) {
    var mod = ng.module('countryModule', ['CrudModule']);

    mod.constant('country.context', 'countries');

    mod.constant('country.skipMock', false);

    mod.constant('countryModel', [{
            name: 'name',
            displayName: 'Name',
            type: 'String',
            required: true,
            searchable: true
        }, {
            name: 'population',
            displayName: 'Population',
            type: 'Integer',
            required: true
        }, {
            name: 'foundation',
            displayName: 'Foundation',
            type: 'Date',
            required: true
        }]);
})(window.angular);