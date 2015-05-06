(function (ng) {
    var mod = ng.module('sportModule');

    mod.service('sportService', ['CRUDBase', 'sport.context', function (CRUDBase, ctx) {
            this.url = ctx;
            CRUDBase.extendService(this);
        }]);

    mod.factory('sportModel', [function () {
            return {
                name:{
                    displayName: 'Name',
                    type: 'String',
                    order: 1
                }, 
                minAge: {
                    displayName: 'MinAge',
                    type: 'Integer',
                    order: 2
                }, 
                maxAge: {
                    displayName: 'MaxAge',
                    type: 'Integer',
                    order: 3
                }, 
                avgAge: {
                    displayName: 'Average Age',
                    type: 'Computed',
                    order: 4,
                    compute: function(record){
                        return (record.minAge + record.maxAge) / 2;
                    }
                }, 
                country: {
                    displayName: 'Country',
                    type: 'Computed',
                    order: 5,
                    compute: function(record){
                        return record.country;
                    }
                }};
        }]);
})(window.angular);
