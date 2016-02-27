'use strict';

angular.module('jhipstercrmdemoApp')
    .controller('FruitpackDetailController', function ($scope, $rootScope, $stateParams, entity, Fruitpack, Orders) {
        $scope.fruitpack = entity;
        $scope.load = function (id) {
            Fruitpack.get({id: id}, function(result) {
                $scope.fruitpack = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipstercrmdemoApp:fruitpackUpdate', function(event, result) {
            $scope.fruitpack = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
