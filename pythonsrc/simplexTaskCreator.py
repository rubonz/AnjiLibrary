# coding=utf-8
__author__ = 'siredvin'

import fractions

# Опишемо усі види команд:
# На початку, 1 - максимізація, 0 - мінімізація
# У обмеженнях: 1 - більше або рівне, 2 - менше або рівне


def switch_case(case):
    return {
        '1': "SignType.GREAT_THEN_OR_EQL",
        '2': "SignType.LOWER_THEN_OR_EQL",
        '0': "SignType.EQL"
    }.get(case, "an out of range number")


def switch_case_1(case):
    return {
        '1': ">=",
        '2': "<=",
        '0': "==",
    }.get(case, "an out of range number")


def transform_to_function(function_string):
    assert isinstance(function_string, str)
    coef = function_string.split(",")
    i = 0
    result = ""
    for el in coef:
        d = int(el)
        i += 1
        if d != 0:
            if d > 0:
                result += "+" + str(d) + "*x" + str(i)
            else:
                result += str(d) + "*x" + str(i)
    return result, i


def write_to_mathematica(fo, is_maximize, target_function, f_conditionals, f_count):
    param_list = "{"
    for i in list(range(1, f_count)):
        param_list += "x" + str(i) + ','
    param_list += "x" + str(f_count) + "}"
    fo.write(param_list)
    fo.write("/.")
    if is_maximize:
        fo.write("Maximize[{")
    else:
        fo.write("Minimize[{")
    fo.write(target_function + ", ")
    for cond in f_conditionals[:len(f_conditionals) - 1]:
        fo.write(cond + " && ")
    fo.write(f_conditionals[len(f_conditionals) - 1])
    fo.write("},")
    fo.write(param_list)
    fo.write("][[2]]\n")


fi = open("task1.txt")
fo1 = open("result_java.txt", "w")
fo2 = open("result_math.txt", "w")
s = fi.readlines()
fi.close()
conditionals = []
targetFunction = ""
isMaximize = True
isTargetFunction = True
count = 0
for stri in s:
    assert isinstance(stri, str)
    if stri[0] == '_':
        fo1.write(
            "task = new SimpleLinearOptimizeTask(" + str(isMaximize).lower() + ",targetFunction,conditionalList);\n"
                                                                               "taskList.add(task);\n")
        write_to_mathematica(fo2, isMaximize, targetFunction, conditionals, count)
        conditionals.clear()
        isTargetFunction = True
        continue
    if isTargetFunction:
        isMaximize = bool(int(stri[len(stri) - 2]))
        #fo1.write("isMaximize = %s;\n" % str(isMaximize).lower())
        fo1.write("targetFunction = new LinearMultiPolynomial(%s);\n" % stri[:len(stri) - 3])
        targetFunction, count = transform_to_function(stri[:len(stri) - 3])
        isTargetFunction = False
        fo1.write("conditionalList = new ArrayList<>();\n")
    else:
        params = stri.split("|")
        fo1.write("conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(%s), " % params[0])
        fo1.write("%s, " % switch_case(params[1][0]))
        fo1.write("%d));\n" % int(params[1][2:]))
        temp, count = transform_to_function(params[0])
        conditionals.append(temp + switch_case_1(params[1][0]) + params[1][2:len(params[1]) - 1])

fo1.write("task = new SimpleLinearOptimizeTask(" + str(isMaximize).lower() + ",targetFunction,conditionalList);\n"
          "taskList.add(task);\n")
fo1.close()
write_to_mathematica(fo2, isMaximize, targetFunction, conditionals, count)
fo2.close()