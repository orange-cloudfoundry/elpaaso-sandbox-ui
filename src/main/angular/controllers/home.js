angular.module('sandbox').controller('home', function ($scope, $http) {
    $scope.showprogressbar = true;
    $scope.connectionPhrase = "";
    $scope.messageTitle = 'Please wait...';
    $scope.messageBody = 'We\'re setting up the Cloud Foundry platform with a private space.';
    $scope.showConnectionDetails = false;

    $http.get('v1/sandboxes/me').success(function (data) {
            $scope.showprogressbar = false;
            $scope.connectionPhrase = "cf login --skip-ssl-validation -a " + data.apiUrl + " -u " + data.spaceName + " -o " + data.orgName + " -s " + data.spaceName;
            $scope.sandbox = data;
            $scope.messageTitle = "You're all set.";
            $scope.messageBody = "You're all set to discover the Cloud Foundry platform: a private space is now allocated to you for playing and trying things out.";
            $scope.showConnectionDetails = true;
        })
        .error(function (error) {
            $scope.showprogressbar = false;
            $scope.messageTitle = "Sorry, something goes wrong.";
            $scope.messageBody = "Error: " + error;
            Materialize.toast("Try again or contact your administrator !", 5000, 'rounded');
            jQuery('#toast-container').click(function(){
                jQuery(this).remove();
            });
        });
    $scope.showMessageCopied = function () {
        Materialize.toast('Copied !', 2000);
    };

});