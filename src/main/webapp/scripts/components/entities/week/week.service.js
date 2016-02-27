'use strict';

angular.module('jhipstercrmdemoApp')
    .factory('Week', function ($resource, DateUtils) {
        return $resource('api/weeks/:id', {}, {
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
    });
