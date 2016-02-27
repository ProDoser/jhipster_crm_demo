'use strict';

angular.module('jhipstercrmdemoApp')
    .controller('CustomerDetailController', function ($scope, $rootScope, $stateParams, entity, Customer, Orders) {
        $scope.customer = entity;
        $scope.load = function (id) {
            Customer.get({id: id}, function(result) {
                $scope.customer = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipstercrmdemoApp:customerUpdate', function(event, result) {
            $scope.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
