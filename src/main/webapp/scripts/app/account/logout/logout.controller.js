'use strict';

angular.module('jtraceApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
