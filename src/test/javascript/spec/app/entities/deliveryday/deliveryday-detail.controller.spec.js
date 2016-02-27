'use strict';

describe('Controller Tests', function() {

    describe('Deliveryday Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDeliveryday, MockOrders;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDeliveryday = jasmine.createSpy('MockDeliveryday');
            MockOrders = jasmine.createSpy('MockOrders');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Deliveryday': MockDeliveryday,
                'Orders': MockOrders
            };
            createController = function() {
                $injector.get('$controller')("DeliverydayDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipstercrmdemoApp:deliverydayUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
