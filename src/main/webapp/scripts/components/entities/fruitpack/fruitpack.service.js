'use strict';

angular.module('jhipstercrmdemoApp')
    .factory('Fruitpack', function ($resource, DateUtils) {
        return $resource('api/fruitpacks/:id', {}, {
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
