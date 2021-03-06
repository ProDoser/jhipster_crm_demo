'use strict';

angular.module('jhipstercrmdemoApp')
    .controller('CustomerController', function ($scope, $state, Customer, CustomerSearch, ParseLinks) {

        $scope.customers = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Customer.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.customers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CustomerSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.customers = result;
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
            $scope.customer = {
                name: null,
                contactperson: null,
                phone: null,
                email: null,
                country: null,
                city: null,
                postcode: null,
                street: null,
                comment: null,
                isprivate: false,
                id: null
            };
        };
    });
