'use strict';

angular.module('jtraceApp')
    .factory('CountService', function CountService($resource) {
        return $resource('api/count', {}, {
            'get': { method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response;
                    }
                }
            }
        });
    });