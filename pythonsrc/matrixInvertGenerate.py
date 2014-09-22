__author__ = 'siredvin'

import random


def write_row_to_math(_file, _row):
    _file.write('{')
    for __x in _row[:len(_row)-1]:
        _file.write(str(__x))
        _file.write(',')
    _file.write(str(_row[len(_row)-1]))
    _file.write('}')


def write_matrix_to_math(_file, _matrix):
    fo2.write("Det[{")
    for __row in _matrix[:len(_matrix)-1]:
        write_row_to_math(_file, __row)
        _file.write(",")
    write_row_to_math(_file, _matrix[len(_matrix)-1])
    fo2.write("}]\n")


def write_matrix_to_python(_file, _matrix):
    _file.write(str(_matrix))
    _file.write("\n")

fo1 = open("result_python.txt", "w")
fo2 = open("result_math.txt", "w")
max = 40
matrix_size = [6, 5, 3, 2]
matrixs = []
#Генерується купа матриць
for k in matrix_size:
    for l in range(4):
        matrix = []
        for i in range(k):
            row = []
            for j in range(k):
                row.append(random.randint(0, max))
            matrix.append(row)
        matrixs.append(matrix)
#print(matrixs, "\n")
#print(matrixs[:len(matrixs)-1])
for matrix in matrixs:
    #print(matrix)
    write_matrix_to_math(fo2, matrix)
    write_matrix_to_python(fo1, matrix)
fo1.close()
fo1.close()
