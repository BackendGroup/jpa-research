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
            function extendCtrl(scope, model, svc) {
                //Variables para el scope
                scope.model = model;
                scope.currentRecord = {};
                scope.records = [];

                //Variables de paginacion
                this.maxSize = 5;
                this.itemsPerPage = 5;
                this.totalItems = 0;
                this.currentPage = 1;

                this.readOnly = false;

                //Variables para el controlador
                this.editMode = false;
                this.error = {show: false};
                var self = this;

                this.showError = function (response) {
                    self.error = {show: true, msg: response.data};
                };

                this.closeError = function () {
                    self.error = {show: false};
                };

                //Funciones que no requieren del servicio
                this.createRecord = function () {
                    this.editMode = true;
                    scope.currentRecord = {};
                };

                this.editRecord = function (record) {
                    return svc.fetchRecord(record).then(function (data) {
                        scope.currentRecord = data;
                        self.editMode = true;
                        return data;
                    });
                };

                //Funciones que usan el servicio CRUD
                this.pageChanged = function () {
                    this.fetchRecords();
                };

                this.fetchRecords = function () {
                    return svc.fetchRecords(this.currentPage, this.itemsPerPage).then(function (data) {
                        scope.records = data;
                        self.totalItems = data.totalRecords;
                        scope.currentRecord = {};
                        self.editMode = false;
                        return data;
                    }, this.showError);
                };
                this.saveRecord = function () {
                    return svc.saveRecord(scope.currentRecord).then(function () {
                        self.fetchRecords();
                    }, this.showError);
                };
                this.deleteRecord = function (record) {
                    return svc.deleteRecord(record).then(function () {
                        self.fetchRecords();
                    }, this.showError);
                };
                this.globalActions = actionsBuilder.buildGlobalActions(this);
                this.recordActions = actionsBuilder.buildRecordActions(this);
            }
            function extendSvc(url) {
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
                this.extendController = function (ctrl, scope, model) {
                    extendCtrl.call(ctrl, scope, model, this);
                };
            }
            this.extendService = function (svc, ctx) {
                extendSvc.call(svc, ctx);
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
