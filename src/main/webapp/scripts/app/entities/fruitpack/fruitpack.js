'use strict';

angular.module('jhipstercrmdemoApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fruitpack', {
                parent: 'entity',
                url: '/fruitpacks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jhipstercrmdemoApp.fruitpack.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fruitpack/fruitpacks.html',
                        controller: 'FruitpackController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fruitpack');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('fruitpack.detail', {
                parent: 'entity',
                url: '/fruitpack/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jhipstercrmdemoApp.fruitpack.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fruitpack/fruitpack-detail.html',
                        controller: 'FruitpackDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fruitpack');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Fruitpack', function($stateParams, Fruitpack) {
                        return Fruitpack.get({id : $stateParams.id});
                    }]
                }
            })
            .state('fruitpack.new', {
                parent: 'fruitpack',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/fruitpack/fruitpack-dialog.html',
                        controller: 'FruitpackDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    price: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fruitpack', null, { reload: true });
                    }, function() {
                        $state.go('fruitpack');
                    })
                }]
            })
            .state('fruitpack.edit', {
                parent: 'fruitpack',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/fruitpack/fruitpack-dialog.html',
                        controller: 'FruitpackDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Fruitpack', function(Fruitpack) {
                                return Fruitpack.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fruitpack', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('fruitpack.delete', {
                parent: 'fruitpack',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/fruitpack/fruitpack-delete-dialog.html',
                        controller: 'FruitpackDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Fruitpack', function(Fruitpack) {
                                return Fruitpack.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fruitpack', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
