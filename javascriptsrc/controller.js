function TestCtrl($scope){
    var demoCPU = initDemoCPU();
    $scope.registers = demoCPU.register.registerMap;
    $scope.registersName = registersName;
    $scope.resultArea = "";
    $scope.runConvert = function(){
        $scope.resultArea += BinToViewBin(demoCPU.commandParser.parse(demoCPU.commandParser.createCommand($scope.codeArea)))+ "\n";
    }
}