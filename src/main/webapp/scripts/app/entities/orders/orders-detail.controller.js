'use strict';

angular.module('jhipstercrmdemoApp')
    .controller('OrdersDetailController', function ($scope, $rootScope, $stateParams, entity, Orders, Customer, Fruitpack, Week, Deliveryday) {
        $scope.orders = entity;
        $scope.load = function (id) {
            Orders.get({id: id}, function(result) {
                $scope.orders = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipstercrmdemoApp:ordersUpdate', function(event, result) {
            $scope.orders = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
