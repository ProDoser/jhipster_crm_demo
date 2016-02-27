'use strict';

angular.module('jhipstercrmdemoApp')
    .factory('DeliverydaySearch', function ($resource) {
        return $resource('api/_search/deliverydays/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
