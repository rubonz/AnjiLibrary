function TestCtrl($scope){
    var demoCPU = initDemoCPU();
    var registerHolder = initRegisterHolder();
    $scope.registers = registerHolder.registerMap;
    $scope.registersName = registersName;
    $scope.resultArea = "";
    var alu = initALU(registerHolder);
    $scope.runConvert = function(){
        $scope.resultArea = "";
        angular.forEach($scope.codeArea.split('\n'),function(value,key){
            var binResult =demoCPU.commandParser.parse(demoCPU.commandParser.createCommand(value));
            $scope.resultArea += BinToViewBin(binResult)+ "\n";
            alu.calculate(binResult);
        });
    }
}