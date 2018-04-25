angular.module("DataLoad", [])
    .factory('httpInterceptor', ['$q', '$location', '$window', function ($q, $location, $window) {
        return {
            request: function (config) {
                if (config) {
                    if (!config.headers) {
                        config.headers = {};
                    }
                    config.headers['x-requested-with'] = 'ajax'
                }
                // do something on request success
                return config || $q.when(config);
            },
            responseError: function (response) {
                if (response && response.status == 401) {
                    var forward = "/views/login.html?forward=" + $location.absUrl();
                    $window.open(forward, "_self");
                }else{
                    return $q.reject(response);
                }
            }
        };
    }])
    .factory("DataLoadService", ['$http', '$q',
        function ($http, $q) {
            return {
                get: function (url) {
                    return $http({
                        method: 'GET', url: url,
                        transformResponse: appendTransform($http.defaults.transformResponse, function (value) {
                            return value;
                        })
                    });
                },
                get: function (url, params) {
                    return $http({
                        method: 'GET',
                        url: url,
                        params: params
                    });
                },
                put: function (url, params) {
                    return $http({
                        method: 'PUT',
                        url: url,
                        data: params,
                        contentType: "application/json; charset=utf-8"
                    });
                },
                post: function (url, params) {
                    return $http({
                        method: 'POST',
                        url: url,
                        data: params,
                        contentType: "application/json; charset=utf-8"
                    });
                },
                //used for case swal is not needed
                delete: function (url) {
                    return $http({
                        url: url,
                        method: 'DELETE',
                        contentType: "application/json; charset=utf-8"
                    })
                },
                save: function (url, params, success, failure) {
                    if (!params['id']) {
                        $q.when(this.post(url, params)).then(function (response) {
                            if (success) {
                                success(response);
                            }
                        }, function (response) {
                            if (failure) {
                                failure(response);
                            }
                            swalFailure(response);
                        });
                    } else {
                        $q.when(this.put(url, params)).then(function (response) {
                            if (success) {
                                success(response);
                            }
                        }, function (response) {
                            if (failure) {
                                failure(response);
                            }
                        });
                    }
                }
            }
        }]
    );
function appendTransform(defaults, transform) {

    // We can't guarantee that the default transformation is an array
    defaults = angular.isArray(defaults) ? defaults : [defaults];

    // Append the new transformation to the defaults
    return defaults.concat(transform);
}
