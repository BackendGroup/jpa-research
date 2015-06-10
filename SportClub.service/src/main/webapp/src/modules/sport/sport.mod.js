(function (ng) {
    var mod = ng.module('sportModule', ['CrudModule', 'countryModule']);

    mod.constant('sport.context', 'sports');

    mod.constant('sportModel', [{
            name: 'name',
            displayName: 'Name',
            type: 'String',
            required: true
        }, {
            name: 'minAge',
            displayName: 'MinAge',
            type: 'Integer',
            required: true
        }, {
            name: 'maxAge',
            displayName: 'MaxAge',
            type: 'Integer',
            required: true
        }, {
            name: 'avgAge',
            displayName: 'Average Age',
            type: 'Computed',
            fn: function (record) {
                return (record.minAge + record.maxAge) / 2;
            }
        }, {
            name: 'rules',
            displayName: 'Rules',
            type: 'String',
            required: true
        }, {
            name: 'country',
            displayName: 'Country',
            type: 'Reference',
            service: 'countryService',
            options: [],
            required: true
        }]);
})(window.angular);