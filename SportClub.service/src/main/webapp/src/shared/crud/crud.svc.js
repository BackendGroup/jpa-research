(function (ng) {
    var crud = ng.module('CrudModule');

    crud.service('actionsService', [function () {
            this.buildGlobalActions = function (ctrl) {
                return [{
                        name: 'create',
                        displayName: 'Create',
                        icon: 'plus',
                        fn: function () {
                            ctrl.createRecord();
                        },
                        show: function () {
                            return !ctrl.readOnly && !ctrl.editMode;
                        }
                    }, {
                        name: 'refresh',
                        displayName: 'Refresh',
                        icon: 'refresh',
                        fn: function () {
                            ctrl.fetchRecords();
                        },
                        show: function () {
                            return !ctrl.editMode;
                        }
                    }, {
                        name: 'save',
                        displayName: 'Save',
                        icon: 'floppy-disk',
                        fn: function () {
                            ctrl.saveRecord();
                        },
                        show: function () {
                            return !ctrl.readOnly && ctrl.editMode;
                        }
                    }, {
                        name: 'cancel',
                        displayName: 'Cancel',
                        icon: 'remove',
                        fn: function () {
                            ctrl.fetchRecords();
                        },
                        show: function () {
                            return !ctrl.readOnly && ctrl.editMode;
                        }
                    }
                ];
            };
            this.buildRecordActions = function (ctrl) {
                return [{
                        name: 'edit',
                        displayName: 'Edit',
                        icon: 'edit',
                        fn: function (rc) {
                            ctrl.editRecord(rc);
                        },
                        show: function () {
                            return !ctrl.readOnly;
                        }
                    }, {
                        name: 'delete',
                        displayName: 'Delete',
                        icon: 'minus',
                        fn: function (rc) {
                            ctrl.deleteRecord(rc);
                        },
                        show: function () {
                            return !ctrl.readOnly;
                        }
                    }];
            };
        }]);

    crud.service('CRUDBase', ['Restangular', '$timeout', 'actionsService', function (RestAngular, $timeout, actionsBuilder) {
            function crudConstructor() {
                this.api = RestAngular.all(this.url);

                this.fetchRecords = function (currentPage, itemsPerPage) {
                    return this.api.getList({page: currentPage, maxRecords: itemsPerPage});
                };

                this.fetchRecord = function (record) {
                    return record.get();
                };
                this.saveRecord = function (currentRecord) {
                    if (currentRecord.id) {
                        return currentRecord.put();
                    } else {
                        return this.api.post(currentRecord);
                    }
                };
                this.deleteRecord = function (record) {
                    return record.remove();
                };
                this.extendCtrl = function (ctrl, scope) {
                    //Variables para el scope
                    scope.currentRecord = {};
                    scope.records = [];

                    //Variables de paginacion
                    ctrl.maxSize = 5;
                    ctrl.itemsPerPage = 5;
                    ctrl.totalItems = 0;
                    ctrl.currentPage = 1;

                    ctrl.readOnly = false;

                    //Variables para el controlador
                    ctrl.editMode = false;
                    ctrl.error = {show: false};

                    ctrl.showError = function (response) {
                        ctrl.error = {show: true, msg: response.data};
                        $timeout(function () {
                            ctrl.error = {show: false};
                        }, 3000);
                    };

                    //Funciones que no requieren del servicio
                    ctrl.createRecord = function () {
                        this.editMode = true;
                        scope.currentRecord = {};
                    };
                    ctrl.editRecord = function (record) {
                        return service.fetchRecord(record).then(function (data) {
                            scope.currentRecord = data;
                            ctrl.editMode = true;
                            return data;
                        });
                    };

                    //Funciones que usan el servicio CRUD
                    var service = this;

                    ctrl.pageChanged = function () {
                        this.fetchRecords();
                    };

                    ctrl.fetchRecords = function () {
                        return service.fetchRecords(ctrl.currentPage, ctrl.itemsPerPage).then(function (data) {
                            scope.records = data;
                            ctrl.totalItems = data.totalRecords;
                            scope.currentRecord = {};
                            ctrl.editMode = false;
                            return data;
                        }, ctrl.showError);
                    };
                    ctrl.saveRecord = function () {
                        return service.saveRecord(scope.currentRecord).then(function () {
                            ctrl.fetchRecords();
                        }, ctrl.showError);
                    };
                    ctrl.deleteRecord = function (record) {
                        var self = this;
                        return service.deleteRecord(record).then(function () {
                            self.fetchRecords();
                        }, ctrl.showError);
                    };
                    ctrl.globalActions = actionsBuilder.buildGlobalActions(ctrl);
                };
            }
            this.extendService = function (svc) {
                crudConstructor.call(svc);
            };
        }]);
})(window.angular);
