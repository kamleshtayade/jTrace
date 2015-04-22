'use strict';

angular.module('jtraceApp')
    .factory('Itemsubcat', function ($resource) {
        return $resource('api/itemsubcats/:id', {}, {
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
