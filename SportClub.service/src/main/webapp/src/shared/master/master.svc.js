(function (ng) {
    var mod = ng.module('masterModule');

    mod.service('masterUtils', ['CRUDBase', 'actionsService', function (CRUDBase, actionsBuilder) {
            function childConstructor(scope, childName) {
                scope.currentRecord = {};
                var self = this;
                scope.$on('master-selected', function (event, args) {
                    scope.records = args[childName];
                    self.fetchRecords();
                });
                this.editMode = false;
                this.error = {show: false};

                this.globalActions = actionsBuilder.buildGlobalActions(this);

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

                this.showError = function (response) {
                    this.error = {show: true, msg: response.data};
                    $timeout(function () {
                        self.error = {show: false};
                    }, 3000);
                };
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
            function masterConstructor() {
                var oldExtend = this.extendCtrl;
                this.extendCtrl = function (ctrl, scope) {
                    oldExtend.call(this, ctrl, scope);
                    var oldEdit = ctrl.editRecord;
                    ctrl.editRecord = function (record) {
                        return oldEdit.call(this, record).then(function (data) {
                            scope.$broadcast('master-selected', data);
                        });
                    };
                    ctrl.changeTab = function (tab) {
                        scope.tab = tab;
                    };
                };
            }
            this.extendChildCtrl = function (ctrl, scope, childName) {
                childConstructor.call(ctrl, scope, childName);
            };
            this.extendService = function (svc) {
                CRUDBase.extendService(svc);
                masterConstructor.call(svc);
            };
        }]);
})(window.angular);