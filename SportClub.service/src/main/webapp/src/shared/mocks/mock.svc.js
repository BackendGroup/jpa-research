(function (ng) {
    var mock = ng.module('MockModule');

    mock.value('MockModule.mockRecords', {});

    mock.service('MockModule.service', ['$httpBackend', 'MockModule.mockRecords', 'MockModule.baseUrl', function ($httpBackend, records, baseUrl) {
            this.getIdUrl = function(url){
                return new RegExp(url + '/([0-9]+)');
            };
            
            this.getQueryUrl = function(url){
                return new RegExp(url + '[?].*');
            };
            
            this.getQueryParams = function (url) {
                var vars = {}, hash;
                var hashes = url.slice(url.indexOf('?') + 1).split('&');
                for (var i = 0; i < hashes.length; i++)
                {
                    hash = hashes[i].split('=');
                    vars[hash[0]] = hash[1];
                }
                return vars;
            };

            this.crudMock = function (entity_url) {
                records[entity_url] = [];
                var fullUrl = baseUrl + '/' + entity_url;
                var fetchUrl = this.getQueryUrl(fullUrl);
                var url_regexp = this.getIdUrl(fullUrl);
                $httpBackend.whenGET(fetchUrl).respond(function (method, url, data, headers) {
                    var responseObj = [];
                    var queryParams = getUrlVars(url);
                    var page = queryParams["page"];
                    var maxRecords = queryParams["maxRecords"];
                    if (page && maxRecords) {
                        var start_index = (page - 1) * maxRecords;
                        var end_index = start_index + maxRecords;
                        responseObj = mockRecords[entity_url].slice(start_index, end_index);
                    } else {
                        responseObj = mockRecords[entity_url];
                    }
                    return [200, responseObj, {"X-Total-Count": records[entity_url].length}];
                });
                $httpBackend.whenGET(url_regexp).respond(function (method, url) {
                    var id = parseInt(url.split('/').pop());
                    var record;
                    ng.forEach(records[entity_url], function (value) {
                        if (value.id === id) {
                            record = ng.copy(value);
                        }
                    });
                    return [200, record, {}];
                });
                $httpBackend.whenPOST(fullUrl).respond(function (method, url, data) {
                    var record = ng.fromJson(data);
                    record.id = Math.floor(Math.random() * 10000);
                    records[entity_url].push(record);
                    return [200, record, {}];
                });
                $httpBackend.whenPUT(url_regexp).respond(function (method, url, data) {
                    var record = ng.fromJson(data);
                    ng.forEach(records[entity_url], function (value, key) {
                        if (value.id === record.id) {
                            records[entity_url].splice(key, 1, record);
                        }
                    });
                    return [200, null, {}];
                });
                $httpBackend.whenDELETE(url_regexp).respond(function (method, url) {
                    var id = parseInt(url.split('/').pop());
                    ng.forEach(records[entity_url], function (value, key) {
                        if (value.id === id) {
                            records[entity_url].splice(key, 1);
                        }
                    });
                    return [200, null, {}];
                });
            };
            
            this.skipCrud = function(entity_url){
                var fullUrl = baseUrl + '/' + entity_url;
                var fetchUrl = this.getQueryUrl(fullUrl);
                var url_regexp = this.getIdUrl(fullUrl);
                $httpBackend.whenGET(fetchUrl).passThrough();
                $httpBackend.whenGET(url_regexp).passThrough();
                $httpBackend.whenPOST(fullUrl).passThrough();
                $httpBackend.whenPUT(url_regexp).passThrough();
                $httpBackend.whenDELETE(url_regexp).passThrough();
            };
        }]);

    mock.provider('MockModule.urls', function () {
        var context = [];
        this.registerUrl = function (value, skip) {
            context.push({url: value, skip: !!skip});
        };
        this.$get = function () {
            return context;
        };
    });
})(window.angular);