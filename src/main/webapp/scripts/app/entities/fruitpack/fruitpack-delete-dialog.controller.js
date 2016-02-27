'use strict';

angular.module('jhipstercrmdemoApp')
	.controller('FruitpackDeleteController', function($scope, $uibModalInstance, entity, Fruitpack) {

        $scope.fruitpack = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Fruitpack.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
