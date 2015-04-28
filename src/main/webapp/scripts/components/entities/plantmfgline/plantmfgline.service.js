'use strict';

angular.module('jtraceApp')
    .factory('Plantmfgline', function ($resource) {
        return $resource('api/plantmfglines/:id', {}, {
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