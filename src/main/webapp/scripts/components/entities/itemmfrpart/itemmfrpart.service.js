'use strict';

angular.module('jtraceApp')
    .factory('Itemmfrpart', function ($resource) {
        return $resource('api/itemmfrparts/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    if (data.activefrom != null){
                        var activefromFrom = data.activefrom.split("-");
                        data.activefrom = new Date(new Date(activefromFrom[0], activefromFrom[1] - 1, activefromFrom[2]));
                    }
                    if (data.activetill != null){
                        var activetillFrom = data.activetill.split("-");
                        data.activetill = new Date(new Date(activetillFrom[0], activetillFrom[1] - 1, activetillFrom[2]));
                    }
                    if (data.formdate != null) data.formdate = new Date(data.formdate);
                    return data;
                }
            },
            'update': { method:'PUT' ,
                transformRequest: function (data) {
                    var activefrom = new Date();
                    activefrom.setUTCDate(data.activefrom.getDate());
                    activefrom.setUTCMonth(data.activefrom.getMonth());
                    activefrom.setUTCFullYear(data.activefrom.getFullYear());
                    data.activefrom = activefrom;
                    var activetill = new Date();
                    activetill.setUTCDate(data.activetill.getDate());
                    activetill.setUTCMonth(data.activetill.getMonth());
                    activetill.setUTCFullYear(data.activetill.getFullYear());
                    data.activetill = activetill;
                    return angular.toJson(data);
                }
            }
        });
    });
