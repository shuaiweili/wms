
var wms = angular.module("wmsApp", ['ngRoute', 'DataLoad']);


/**
 * define all uri route rules
 */
wms.config(["$routeProvider",
    function ($routeProvider) {
        $routeProvider
            .when("/form", {
                       templateUrl: 'views/forms.html',
                       reloadOnSearch: false
                   }
        );
    }]);