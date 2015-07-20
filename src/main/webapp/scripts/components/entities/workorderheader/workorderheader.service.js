'use strict';

angular.module('jtraceApp')
    .factory('Workorderheader', function ($resource) {
        return $resource('api/workorderheaders/:id', {}, {
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
    })
    .factory('Rwoheader',function($resource){
        return $resource('api/reportwoheaders/:id',{},{
            'query':{method:'GET',isArray:true},
            'get':{
                method:'GET',
                transformResponse:function(data){
                    data=angular.fromJson(data);
                    return data;
                }
            }
        });
    });
