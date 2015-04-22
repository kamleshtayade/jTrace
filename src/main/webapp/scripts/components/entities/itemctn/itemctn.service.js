'use strict';

angular.module('jtraceApp')
    .factory('Itemctn', function ($resource) {
        return $resource('api/itemctns/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    var recdDtFrom = data.recdDt.split("-");
                    data.recdDt = new Date(new Date(recdDtFrom[0], recdDtFrom[1] - 1, recdDtFrom[2]));
                    return data;
                }
            }
        });
    });
