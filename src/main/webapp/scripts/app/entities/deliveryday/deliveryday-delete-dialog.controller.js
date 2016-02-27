'use strict';

angular.module('jhipstercrmdemoApp')
	.controller('DeliverydayDeleteController', function($scope, $uibModalInstance, entity, Deliveryday) {

        $scope.deliveryday = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Deliveryday.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
