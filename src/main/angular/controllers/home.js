/*
 * Copyright (C) 2015-2016 Orange
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
            $scope.messageBody = "Error: " + error.message;
            Materialize.toast("Try again or contact your administrator !", 5000, 'rounded');
            jQuery('#toast-container').click(function(){
                jQuery(this).remove();
            });
        });
    $scope.showMessageCopied = function () {
        Materialize.toast('Copied !', 2000);
    };

});
