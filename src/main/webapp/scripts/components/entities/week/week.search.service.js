'use strict';

angular.module('jhipstercrmdemoApp')
    .factory('WeekSearch', function ($resource) {
        return $resource('api/_search/weeks/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
