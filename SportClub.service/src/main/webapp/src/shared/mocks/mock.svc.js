(function (ng) {
    var mock = ng.module('MockModule');

    mock.value('MockModule.mockRecords', {});

    mock.service('MockModule.service', ['$httpBackend', 'MockModule.mockRecords', 'MockModule.baseUrl', function ($httpBackend, mockRecords, baseUrl) {
            function getIdUrl(url) {
                return new RegExp(url + '/([0-9]+)');
            }

            function getQueryUrl(url) {
                return new RegExp(url + '([?].*)?');
            }

            function getQueryParams(url) {
                var vars = {}, hash;
                var hashes = url.slice(url.indexOf('?') + 1).split('&');
                for (var i = 0; i < hashes.length; i++)
                {
                    hash = hashes[i].split('=');
                    vars[hash[0]] = hash[1];
                }
                return vars;
            }

            function crudMock(entity_url) {
                mockRecords[entity_url] = [];
                var records = mockRecords[entity_url];
                var fullUrl = baseUrl + '/' + entity_url;
                var fetchUrl = getQueryUrl(fullUrl);
                var url_regexp = getIdUrl(fullUrl);
                $httpBackend.whenGET(fetchUrl).respond(function (method, url) {
                    var responseObj = [];
                    var queryParams = getQueryParams(url);
                    var page = queryParams["page"];
                    var maxRecords = queryParams["maxRecords"];
                    var headers = {};
                    if (page && maxRecords) {
                        var start_index = (page - 1) * maxRecords;
                        var end_index = start_index + maxRecords;
                        responseObj = records.slice(start_index, end_index);
                        headers = {"X-Total-Count": records.length};
                    } else {
                        responseObj = records;
                    }
                    return [200, responseObj, headers];
                });
                $httpBackend.whenGET(url_regexp).respond(function (method, url) {
                    var id = parseInt(url.split('/').pop());
                    var record;
                    ng.forEach(records, function (value) {
                        if (value.id === id) {
                            record = ng.copy(value);
                        }
                    });
                    return [200, record, {}];
                });
                $httpBackend.whenPOST(fullUrl).respond(function (method, url, data) {
                    var record = ng.fromJson(data);
                    record.id = Math.floor(Math.random() * 10000);
                    records.push(record);
                    return [200, record, {}];
                });
                $httpBackend.whenPUT(url_regexp).respond(function (method, url, data) {
                    var record = ng.fromJson(data);
                    ng.forEach(records, function (value, key) {
                        if (value.id === record.id) {
                            records.splice(key, 1, record);
                        }
                    });
                    return [200, null, {}];
                });
                $httpBackend.whenDELETE(url_regexp).respond(function (method, url) {
                    var id = parseInt(url.split('/').pop());
                    ng.forEach(records, function (value, key) {
                        if (value.id === id) {
                            records.splice(key, 1);
                        }
                    });
                    return [200, null, {}];
                });
            }

            function skipCrud(entity_url) {
                var fullUrl = baseUrl + '/' + entity_url;
                var fetchUrl = getQueryUrl(fullUrl);
                var url_regexp = getIdUrl(fullUrl);
                $httpBackend.whenGET(fetchUrl).passThrough();
                $httpBackend.whenGET(url_regexp).passThrough();
                $httpBackend.whenPOST(fullUrl).passThrough();
                $httpBackend.whenPUT(url_regexp).passThrough();
                $httpBackend.whenDELETE(url_regexp).passThrough();
            }

            this.setMock = function (context, skip) {
                if (!!skip) {
                    skipCrud(context);
                } else {
                    crudMock(context);
                }
            };
        }]);
})(window.angular);