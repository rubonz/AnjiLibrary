//region Конструювання необхідних частин
function initRamHolder() {
    var ramHolder = {};
    ramHolder.ramMap = [];
    ramHolder.getDex = function (index) {
        return this.ramMap[index];
    };
    ramHolder.setDex = function (index, value) {
        this.ramMap[index] = value;
    };
    ramHolder.setHex = function (index, value) {
        this.ramMap[HexToDex(index)] = value;
    };
    ramHolder.getHex = function (index) {
        return this.ramMap[HexToDex(index)];
    };
    return ramHolder;
}

function initRegisterHolder() {
    var registerHolder = {};
    var registerMap = [];
    for (var i = 0; i < 32; i++) {
        registerMap[i] = 0;
    }
    registerHolder.registerMap = registerMap;
    registerHolder.get = function (index) {
        if (index > -1 && index < 32) {
            return this.registerMap[index];
        }
    };
    registerHolder.set = function (index, value) {
        if (index > 0 && index < 32) {
            this.registerMap[index] = value;
        }
    };
    return registerHolder;
}

function initCommandRamHolder() {
    var commandRamHolder = {};
    commandRamHolder.PC = 0;
    commandRamHolder.commandCodeList = [];
    commandRamHolder.addCommandCodes = function (code) {
        this.commandCodeList.push(code);
    };
    commandRamHolder.getCurrent = function () {
        var result = this.commandCodeList[this.PC];
        this.PC++;
        return result;
    };
    return commandRamHolder;
}

function initCommandParser() {
    var commandParser = {};
    var parseArray = generateParseArray();
    commandParser.createCommand = function(code){
        var splitedCode = code.split(' ');
        for (var i=1;i<splitedCode.length-1;i++){
            splitedCode[i] = splitedCode[i].substring(0,splitedCode[i].length-1);
        }
        console.log(splitedCode[0]);
        console.log(splitedCode[1]);
        console.log(splitedCode[2]);
        console.log(splitedCode[3]);
        return splitedCode;
    };
    commandParser.parse = function (splitedCode) {
        return parseArray[splitedCode[0]](splitedCode);
    };
    return commandParser;
}
//endregion

function generateParseArray() {
    var parseArray = [];
    parseArray['add'] = function (splitedCode) {
        return "000000" + convertDST(splitedCode) + "0000010000";
    };
    parseArray['addi'] = function(splitedCode){
        return "001000" + convertTSImm(splitedCode);
    };
    parseArray['addiu'] = function(splitedCode){
        return "001001" + convertTSImm(splitedCode);
    };
    parseArray['addu'] = function(splitedCode){
        return "000000" + convertDST(splitedCode) + "00000100001"
    };
    return parseArray;
}

//TODO:докинути тут необхідні нулі до кінця
function convertDST(splitedCode){
    return DexToBin(getRegisterCode(splitedCode[2])) +DexToBin(getRegisterCode(splitedCode[3])) + DexToBin(getRegisterCode(splitedCode[1]));
}

//TODO:докинути тут необхідні нулі до кінця
function convertTSImm(splitedCode){
    return DexToBin(getRegisterCode(splitedCode[2])) + DexToBin(getRegisterCode(splitedCode[1]))
        + DexToBin(parseInt(splitedCode[3]))
        ;
}

registerCode = {
    'zero': 0,
    'at': 1,
    'v0': 2,
    'v1': 3,
    'a0': 4,
    'a1': 5,
    'a2': 6,
    'a3': 7,
    't0': 8,
    't1': 9,
    't2': 10,
    't3': 11,
    't4': 12,
    't5': 13,
    't6': 14,
    't7': 15,
    's0': 16,
    's1': 17,
    's2': 18,
    's3': 19,
    's4': 20,
    's5': 21,
    's6': 22,
    's7': 23,
    't8': 24,
    't9': 25,
    'k0': 26,
    'k1': 27,
    'gp': 28,
    'sp': 29,
    's8': 30,
    'ra': 31
};

numberSet = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9];
/**
 * @param {String} code
 */
function getRegisterCode(code) {
    var exsCode = code.substring(1);
    if (numberSet.indexOf(parseInt(exsCode[0])) == -1) {
        return registerCode[exsCode];
    }
    return parseInt(exsCode);
}

