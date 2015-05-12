(function (ng) {
    var mod = ng.module('masterModule');

    mod.service('masterUtils', [function () {
            function childConstructor(scope) {
                scope.currentRecord = {};
                scope.records = [];
                this.editMode = false;
                this.error = {show: false};

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
                    var self = this;
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
            }

            this.extendService = function (svc, scope) {
                childConstructor.call(svc, scope);
            };
        }]);
})(window.angular);