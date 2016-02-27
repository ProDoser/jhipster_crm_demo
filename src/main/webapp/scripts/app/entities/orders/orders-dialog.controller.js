'use strict';

angular.module('jhipstercrmdemoApp').controller('OrdersDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Orders', 'Customer', 'Fruitpack', 'Week', 'Deliveryday',
        function($scope, $stateParams, $uibModalInstance, entity, Orders, Customer, Fruitpack, Week, Deliveryday) {

        $scope.orders = entity;
        $scope.customers = Customer.query();
        $scope.fruitpacks = Fruitpack.query();
        $scope.weeks = Week.query();
        $scope.deliverydays = Deliveryday.query();
        $scope.load = function(id) {
            Orders.get({id : id}, function(result) {
                $scope.orders = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipstercrmdemoApp:ordersUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.orders.id != null) {
                Orders.update($scope.orders, onSaveSuccess, onSaveError);
            } else {
                Orders.save($scope.orders, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDate = {};

        $scope.datePickerForDate.status = {
            opened: false
        };

        $scope.datePickerForDateOpen = function($event) {
            $scope.datePickerForDate.status.opened = true;
        };
}]);
