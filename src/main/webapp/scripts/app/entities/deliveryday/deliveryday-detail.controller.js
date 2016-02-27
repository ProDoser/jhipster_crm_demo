'use strict';

angular.module('jhipstercrmdemoApp')
    .controller('DeliverydayDetailController', function ($scope, $rootScope, $stateParams, entity, Deliveryday, Orders) {
        $scope.deliveryday = entity;
        $scope.load = function (id) {
            Deliveryday.get({id: id}, function(result) {
                $scope.deliveryday = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipstercrmdemoApp:deliverydayUpdate', function(event, result) {
            $scope.deliveryday = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
