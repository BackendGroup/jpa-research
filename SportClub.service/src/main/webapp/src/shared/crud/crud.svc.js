(function (ng) {
    var mod = ng.module('CrudModule');

    mod.service('actionsService', [function () {
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
                        icon: 'save',
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

    mod.service('CRUDBase', ['Restangular', '$timeout', 'actionsService', function (RestAngular, $timeout, actionsBuilder) {
            function crudConstructor(url) {
                this.url = url;
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
                    };
                    
                    ctrl.closeError = function(){
                        this.error = {show: false};
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
                    ctrl.recordActions = actionsBuilder.buildRecordActions(ctrl);
                };
            }
            this.extendService = function (svc, ctx) {
                crudConstructor.call(svc, ctx);
            };

            function basicCtrl(scope) {
                //Variables para el scope
                scope.currentRecord = {};
                scope.records = [];

                //Variables para el controlador
                this.readOnly = false;
                this.editMode = false;
                this.error = {show: false};

                this.showError = function (response) {
                    this.error = {show: true, msg: response.data};
                    var self = this;
                    $timeout(function () {
                        self.error = {show: false};
                    }, 3000);
                };
            }
            this.extendBasicCtrl = function (ctrl) {

            };
        }]);

    mod.service('modalService', ['$modal', function ($modal) {
            this.createSelectionModal = function (name, items) {
                return $modal.open({
                    animation: true,
                    templateUrl: 'src/shared/crud/modal.tpl.html',
                    controller: 'modalCtrl',
                    resolve: {
                        name: function () {
                            return name;
                        },
                        items: function () {
                            return items;
                        }
                    }
                });
            };
        }]);
})(window.angular);
