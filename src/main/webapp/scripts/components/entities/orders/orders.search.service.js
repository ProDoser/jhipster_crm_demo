'use strict';

angular.module('jhipstercrmdemoApp')
    .factory('OrdersSearch', function ($resource) {
        return $resource('api/_search/orderss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
