'use strict';

angular.module('jtraceApp')
    .factory('Isupplier', function ($resource) {
        return $resource('api/isuppliers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
