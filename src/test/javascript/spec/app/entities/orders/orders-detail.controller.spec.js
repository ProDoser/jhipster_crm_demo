'use strict';

describe('Controller Tests', function() {

    describe('Orders Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockOrders, MockCustomer, MockFruitpack, MockWeek, MockDeliveryday;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockOrders = jasmine.createSpy('MockOrders');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockFruitpack = jasmine.createSpy('MockFruitpack');
            MockWeek = jasmine.createSpy('MockWeek');
            MockDeliveryday = jasmine.createSpy('MockDeliveryday');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Orders': MockOrders,
                'Customer': MockCustomer,
                'Fruitpack': MockFruitpack,
                'Week': MockWeek,
                'Deliveryday': MockDeliveryday
            };
            createController = function() {
                $injector.get('$controller')("OrdersDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipstercrmdemoApp:ordersUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
