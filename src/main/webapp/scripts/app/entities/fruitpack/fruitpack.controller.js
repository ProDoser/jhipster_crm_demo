'use strict';

angular.module('jhipstercrmdemoApp')
    .controller('FruitpackController', function ($scope, $state, Fruitpack, FruitpackSearch, ParseLinks) {

        $scope.fruitpacks = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function() {
            Fruitpack.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.fruitpacks.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.fruitpacks = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            FruitpackSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.fruitpacks = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.fruitpack = {
                name: null,
                price: null,
                description: null,
                id: null
            };
        };
    });
