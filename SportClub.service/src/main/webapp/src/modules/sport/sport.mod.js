(function (ng) {
    var mod = ng.module('sportModule', ['CrudModule', 'MockModule', 'countryModule']);

    mod.constant('sport.context', 'sports');

    mod.constant('sport.skipMock', true);

    mod.run(['sport.context', 'MockModule.service', 'sport.skipMock', function (context, mockService, skip) {
            mockService.setMock(context, skip);
        }]);
})(window.angular);