__author__ = 'siredvin'

fi1 = open("result_python.txt")
fi2 = open("output_math.txt")
fo1 = open("result_java.txt", "w")


def write_row_to_java(_file, _row):
    for x in _row[:len(_row)-1]:
        _file.write(str(x))
        _file.write(',')
    _file.write(str(_row[len(_row)-1]))


matrixs = []
for line in fi1.readlines():
    matrix = eval(line)
    matrixs.append(matrix)
    # print(matrix)
detmatrix = []
for line in fi2.readlines():
    detmatrix.append(int(line))
fi1.close()
fi2.close()
for i in range(len(matrixs)):
    det = detmatrix[i]
    matrix = matrixs[i]
    size = len(matrix[0])
    fo1.write("assertEquals(")
    fo1.write(str(det))
    fo1.write(', det(FixedDoubleMatrix.createInstance(')
    fo1.write(str(size))
    fo1.write(',')
    fo1.write(str(size))
    fo1.write(', new double[]{')
    for row in matrix[:len(matrix)-1]:
        write_row_to_java(fo1, row)
        fo1.write(',')
    write_row_to_java(fo1, matrix[len(matrix)-1])
    fo1.write('})),delta);\n')
fo1.close()