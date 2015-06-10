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

    mod.service('CRUDBase', ['Restangular', 'actionsService', '$injector', function (RestAngular, actionsBuilder, $injector) {
            function extendCtrl(scope, model, svc, name, displayName) {
                //Variables para el scope
                scope.name = name;
                scope.displayName = displayName;
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
                this.error = {show: false, type: 'danger'};
                var self = this;

                this.showMessage = function (msg, type) {
                    var types = ['info', 'danger', 'warning', 'success'];
                    if (types.some(function (rc) {
                        return type === rc;
                    })) {
                        this.error.show = true;
                        this.error.type = type;
                        this.error.msg = msg;
                    }
                };

                this.showError = function (msg) {
                    this.showMessage(msg, 'danger');
                };

                this.showSuccess = function (msg) {
                    this.showMessage(msg, 'success');
                };

                this.showWarning = function (msg) {
                    this.showMessage(msg, 'warning');
                };

                this.showInfo = function (msg) {
                    this.showMessage(msg, 'info');
                };

                this.closeError = function () {
                    this.error.show = false;
                    this.error.msg = "";
                };

                //Funciones del controlador
                this.pageChanged = function () {
                    this.fetchRecords();
                };

                function responseError(response) {
                    self.showError(response.data);
                }

                this.createRecord = function () {
                    this.editMode = true;
                    scope.currentRecord = {};
                };

                this.editRecord = function (record) {
                    return svc.fetchRecord(record).then(function (data) {
                        scope.currentRecord = data;
                        self.editMode = true;
                        return data;
                    }, responseError);
                };

                this.fetchRecords = function () {
                    return svc.fetchRecords(this.currentPage, this.itemsPerPage).then(function (data) {
                        scope.records = data;
                        self.totalItems = data.totalRecords;
                        scope.currentRecord = {};
                        self.editMode = false;
                        return data;
                    }, responseError);
                };
                this.saveRecord = function () {
                    return svc.saveRecord(scope.currentRecord).then(function () {
                        self.fetchRecords();
                    }, responseError);
                };
                this.deleteRecord = function (record) {
                    return svc.deleteRecord(record).then(function () {
                        self.fetchRecords();
                    }, responseError);
                };

                //Código para cargar los valores de las referencias
                this.loadRefOptions = function () {
                    function loadFieldOptions(field) {
                        var svc = $injector.get(field.service);
                        svc.fetchRecords().then(function (data) {
                            field.options = data.plain();
                        });
                    }
                    var model = scope.model;
                    for (var i in model) {
                        if (model.hasOwnProperty(i)) {
                            var field = model[i];
                            if (field.type === 'Reference' && !!field.service) {
                                if ($injector.has(field.service)) {
                                    loadFieldOptions(field);
                                }
                            }
                        }
                    }
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
                this.extendController = function (ctrl, scope, model, name, displayName) {
                    extendCtrl.call(ctrl, scope, model, this, name, displayName);
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
