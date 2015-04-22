'use strict';

angular.module('jtraceApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


