'use strict';

angular.module('jhipstercrmdemoApp').controller('DeliverydayDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Deliveryday', 'Orders',
        function($scope, $stateParams, $uibModalInstance, entity, Deliveryday, Orders) {

        $scope.deliveryday = entity;
        $scope.orderss = Orders.query();
        $scope.load = function(id) {
            Deliveryday.get({id : id}, function(result) {
                $scope.deliveryday = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipstercrmdemoApp:deliverydayUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.deliveryday.id != null) {
                Deliveryday.update($scope.deliveryday, onSaveSuccess, onSaveError);
            } else {
                Deliveryday.save($scope.deliveryday, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
