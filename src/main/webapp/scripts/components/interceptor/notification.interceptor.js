 'use strict';

angular.module('jhipstercrmdemoApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-jhipstercrmdemoApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-jhipstercrmdemoApp-params')});
                }
                return response;
            }
        };
    });
