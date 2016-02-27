'use strict';

angular.module('jhipstercrmdemoApp')
    .factory('FruitpackSearch', function ($resource) {
        return $resource('api/_search/fruitpacks/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
