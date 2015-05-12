(function (ng) {
    var mod = ng.module('MockModule', ['ngMockE2E']);

    mod.constant('MockModule.baseUrl', 'webresources');

    mod.run(['$httpBackend', 'MockModule.baseUrl', function ($httpBackend, baseUrl) {
            var ignore_regexp = new RegExp('^((?!' + baseUrl + ').)*$');
            $httpBackend.whenGET(ignore_regexp).passThrough();
        }]);
})(window.angular);