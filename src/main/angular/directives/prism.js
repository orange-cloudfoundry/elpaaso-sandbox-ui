angular.module('sandbox')
    .directive('ngPrism', ['$interpolate', function ($interpolate) {
        //"use strict";
        return {
            restrict: 'E',
            template: '<pre><code ng-transclude></code></pre>',
            replace: true,
            transclude: true,
            link: function (scope, element) {
                element.ready(function () {
                    var tmp = $interpolate(element.find('code').text())(scope);
                    if (tmp != undefined && tmp != ""){
                        element.find('code').html(Prism.highlightElement(tmp).value);
                    }
                });
            }
        };
    }]);