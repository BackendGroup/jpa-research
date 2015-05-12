(function (ng) {
    var mod = ng.module('countryModule', ['CrudModule', 'MockModule']);

    mod.constant('country.context', 'countries');

    mod.constant('country.skipMock', true);

    mod.run(['country.context', 'MockModule.service', 'country.skipMock', function (context, mockService, skip) {
            mockService.setMock(context, skip);
        }]);

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