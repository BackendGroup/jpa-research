(function (ng) {
    var mod = ng.module('masterModule');

    mod.service('masterUtils', ['CRUDBase', 'actionsService', function (CRUDBase, actionsBuilder) {
            function commonChildCtrl(scope, model, childName, refName) {
                //Atributos del Scope
                scope.model = model;
                scope.refName = refName;
                scope.currentRecord = {};

                //Atributos del controlador
                this.editMode = false;
                this.readOnly = false;
                this.error = {show: false};

                //Función para despliegue de errores
                this.showError = function (response) {
                    this.error = {show: true, msg: response.data};
                };

                this.closeError = function () {
                    this.error = {show: false};
                };

                //Escucha de evento cuando se selecciona un registro maestro
                var self = this;
                scope.$on('master-selected', function (event, args) {
                    scope.records = args[childName];
                    scope.refId = args.id;
                    if (self.fetchRecords) {
                        self.fetchRecords();
                    }
                });
            }
            function compositeRelCtrl(scope, model, childName, refName) {
                commonChildCtrl.call(this, scope, model, childName, refName);

                this.globalActions = actionsBuilder.buildGlobalActions(this);
                this.recordActions = actionsBuilder.buildRecordActions(this);

                //Función para encontrar un registro por ID o CID
                function indexOf(rc) {
                    for (var i in scope.records) {
                        if (scope.records.hasOwnProperty(i)) {
                            var current = scope.records[i];
                            if (current.id === rc.id || current.cid === rc.cid) {
                                return i;
                            }
                        }
                    }
                }
                this.fetchRecords = function () {
                    scope.currentRecord = {};
                    this.editMode = false;
                };
                this.saveRecord = function () {
                    var rc = scope.currentRecord;
                    if (rc.id || rc.cid) {
                        var idx = indexOf(rc);
                        scope.records.splice(idx, 1, rc);
                    } else {
                        rc.cid = -Math.floor(Math.random() * 10000);
                        rc[scope.refName] = scope.refId;
                        scope.records.push(rc);
                    }
                    this.fetchRecords();
                };
                this.deleteRecord = function (record) {
                    var idx = indexOf(record);
                    scope.records.splice(idx, 1);
                };
                this.editRecord = function (record) {
                    scope.currentRecord = ng.copy(record);
                    this.editMode = true;
                };
                this.createRecord = function () {
                    this.editMode = true;
                    scope.currentRecord = {};
                };
            }
            function masterSvcConstructor() {
                var oldExtendFn = this.extendCtrl;
                this.extendCtrl = function (ctrl, scope) {
                    oldExtendFn.call(this, ctrl, scope);
                    var oldEditFn = ctrl.editRecord;
                    ctrl.editRecord = function (record) {
                        return oldEditFn.call(this, record).then(function (data) {
                            scope.$broadcast('master-selected', data);
                            return data;
                        });
                    };
                    ctrl.changeTab = function (tab) {
                        scope.tab = tab;
                    };
                };
            }
            this.extendCompChildCtrl = function (ctrl, scope, model, childName, refName) {
                compositeRelCtrl.call(ctrl, scope, model, childName, refName);
            };
            this.extendService = function (svc) {
                CRUDBase.extendService(svc);
                masterSvcConstructor.call(svc);
            };
            this.extendAggChildCtrl = function (ctrl, scope, model, childName, refName) {

            };
        }]);
})(window.angular);