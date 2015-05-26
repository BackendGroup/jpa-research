(function (ng) {
    var mod = ng.module('sportModule', ['CrudModule', 'countryModule']);

    mod.constant('sport.context', 'sports');

    mod.constant('sportModel', [{
            name: 'name',
            displayName: 'Name',
            type: 'String'
        }, {
            name: 'minAge',
            displayName: 'MinAge',
            type: 'Integer'
        }, {
            name: 'maxAge',
            displayName: 'MaxAge',
            type: 'Integer'
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
            type: 'String'
        }, {
            name: 'country',
            displayName: 'Country',
            type: 'Computed',
            fn: function (record) {
                return record.country;
            }
        }]);
})(window.angular);