'use strict';

angular.module('jhipstercrmdemoApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('deliveryday', {
                parent: 'entity',
                url: '/deliverydays',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jhipstercrmdemoApp.deliveryday.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deliveryday/deliverydays.html',
                        controller: 'DeliverydayController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deliveryday');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('deliveryday.detail', {
                parent: 'entity',
                url: '/deliveryday/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jhipstercrmdemoApp.deliveryday.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deliveryday/deliveryday-detail.html',
                        controller: 'DeliverydayDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deliveryday');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Deliveryday', function($stateParams, Deliveryday) {
                        return Deliveryday.get({id : $stateParams.id});
                    }]
                }
            })
            .state('deliveryday.new', {
                parent: 'deliveryday',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/deliveryday/deliveryday-dialog.html',
                        controller: 'DeliverydayDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    weekday: null,
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('deliveryday', null, { reload: true });
                    }, function() {
                        $state.go('deliveryday');
                    })
                }]
            })
            .state('deliveryday.edit', {
                parent: 'deliveryday',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/deliveryday/deliveryday-dialog.html',
                        controller: 'DeliverydayDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Deliveryday', function(Deliveryday) {
                                return Deliveryday.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('deliveryday', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('deliveryday.delete', {
                parent: 'deliveryday',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/deliveryday/deliveryday-delete-dialog.html',
                        controller: 'DeliverydayDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Deliveryday', function(Deliveryday) {
                                return Deliveryday.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('deliveryday', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
