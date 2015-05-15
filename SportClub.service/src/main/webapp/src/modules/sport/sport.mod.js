(function (ng) {
    var mod = ng.module('sportModule', ['CrudModule', 'countryModule']);

    mod.constant('sport.context', 'sports');
    
    mod.constant('sportModel', [{
            name: 'name',
            displayName: 'Name',
            type: 'String',
            order: 1
        }, {
            name: 'minAge',
            displayName: 'MinAge',
            type: 'Integer',
            order: 2
        }, {
            name: 'maxAge',
            displayName: 'MaxAge',
            type: 'Integer',
            order: 3
        }, {
            name: 'avgAge',
            displayName: 'Average Age',
            type: 'Computed',
            order: 4,
            compute: function (record) {
                return (record.minAge + record.maxAge) / 2;
            }
        }, {
            name: 'rules',
            displayName: 'Rules',
            type: 'String',
            order: 5
        }, {
            name: 'country',
            displayName: 'Country',
            type: 'Computed',
            order: 6,
            compute: function (record) {
                return record.country;
            }
        }]);
})(window.angular);