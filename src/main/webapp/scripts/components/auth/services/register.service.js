'use strict';

angular.module('jhipstercrmdemoApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


