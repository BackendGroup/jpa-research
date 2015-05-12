(function (ng) {
    var mod = ng.module('countryModule', ['CrudModule', 'MockModule']);

    mod.constant('country.context', 'countries');

    mod.constant('country.skipMock', false);

    mod.run(['country.context', 'MockModule.service', 'country.skipMock', function (context, mockService, skip) {
            mockService.setMock(context, skip);
        }]);
})(window.angular);