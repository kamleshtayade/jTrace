'use strict';

angular.module('jtraceApp')
    .factory('Itemtype', function ($resource) {
        return $resource('api/itemtypes/:id', {}, {
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
