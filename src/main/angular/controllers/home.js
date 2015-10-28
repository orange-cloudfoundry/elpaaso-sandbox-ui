angular.module('sandbox').controller('home', function ($scope, $http) {
    $scope.showprogressbar = true;
    $scope.connectionPhrase = "";
    $http.get('v1/sandboxes/me').success(function (data) {
        $scope.showprogressbar = false;
        $scope.connectionPhrase = "cf login --skip-ssl-validation -a " + data.apiUrl + " -u " + data.spaceName + " -o " + data.orgName + " -s " + data.spaceName;
        $scope.sandbox = data;
    });
    $scope.showMessageCopied = function () {
        Materialize.toast('Copied !', 2000);
    };
});