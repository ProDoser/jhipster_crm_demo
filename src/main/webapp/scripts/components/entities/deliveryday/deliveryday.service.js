'use strict';

angular.module('jhipstercrmdemoApp')
    .factory('Deliveryday', function ($resource, DateUtils) {
        return $resource('api/deliverydays/:id', {}, {
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
