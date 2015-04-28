'use strict';

angular.module('jtraceApp')
    .factory('Itemmfrpart', function ($resource) {
        return $resource('api/itemmfrparts/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });