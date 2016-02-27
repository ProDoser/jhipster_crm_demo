'use strict';

angular.module('jhipstercrmdemoApp')
    .controller('DeliverydayController', function ($scope, $state, Deliveryday, DeliverydaySearch) {

        $scope.deliverydays = [];
        $scope.loadAll = function() {
            Deliveryday.query(function(result) {
               $scope.deliverydays = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            DeliverydaySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.deliverydays = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.deliveryday = {
                weekday: null,
                name: null,
                id: null
            };
        };
    });
