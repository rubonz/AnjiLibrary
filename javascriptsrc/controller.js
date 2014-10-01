function TestCtrl($scope){
    var parser = initCommandParser();
    $scope.runConvert = function(){
       $scope.resultArea = parser.parse(parser.createCommand($scope.codeArea));
    }
}