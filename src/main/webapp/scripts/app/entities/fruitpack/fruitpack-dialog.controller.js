'use strict';

angular.module('jhipstercrmdemoApp').controller('FruitpackDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Fruitpack', 'Orders',
        function($scope, $stateParams, $uibModalInstance, entity, Fruitpack, Orders) {

        $scope.fruitpack = entity;
        $scope.orderss = Orders.query();
        $scope.load = function(id) {
            Fruitpack.get({id : id}, function(result) {
                $scope.fruitpack = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipstercrmdemoApp:fruitpackUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.fruitpack.id != null) {
                Fruitpack.update($scope.fruitpack, onSaveSuccess, onSaveError);
            } else {
                Fruitpack.save($scope.fruitpack, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
