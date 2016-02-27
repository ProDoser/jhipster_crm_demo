'use strict';

describe('Controller Tests', function() {

    describe('Fruitpack Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFruitpack, MockOrders;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFruitpack = jasmine.createSpy('MockFruitpack');
            MockOrders = jasmine.createSpy('MockOrders');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Fruitpack': MockFruitpack,
                'Orders': MockOrders
            };
            createController = function() {
                $injector.get('$controller')("FruitpackDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipstercrmdemoApp:fruitpackUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
