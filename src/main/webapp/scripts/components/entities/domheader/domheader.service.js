'use strict';

angular.module('jtraceApp')
    .factory('Domheader', function ($resource) {
        return $resource('api/domheaders/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    if (data.shiftstart != null) data.shiftstart = new Date(data.shiftstart);
                    if (data.shiftend != null) data.shiftend = new Date(data.shiftend);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
