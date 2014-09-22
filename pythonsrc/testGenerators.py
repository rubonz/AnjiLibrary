__author__ = 'siredvin'

import random


def gen_resize_linear_vector_i_negate_test_with_fraction(_file, class_name='ResizeLinearVector', count=3):
    rand = random.Random()
    l = []
    for _ in list(range(count-1)):
        a = rand.randint(-10000, 10000)
        b = rand.randint(-10000, 10000)
        l.append([a, b])
    _file.write('assertEquals((new '+class_name+'<Fraction>(new Fraction[]{new Fraction(')
    for a, b in l:
        _file.write(str(a))
        _file.write(',')
        _file.write(str(b))
        _file.write('),new Fraction(')
    a1 = rand.randint(-10000, 10000)
    b1 = rand.randint(-10000, 10000)
    _file.write(str(a1))
    _file.write(',')
    _file.write(str(b1))
    _file.write(')})).inegate(),new '+class_name+'<Fraction>(new Fraction[]{new Fraction(')
    for a, b in l:
        _file.write(str(-a))
        _file.write(',')
        _file.write(str(b))
        _file.write('),new Fraction(')
    _file.write(str(-a1))
    _file.write(',')
    _file.write(str(b1))
    _file.write(')}));\n')


def gen_resize_linear_vector_negate_test_with_fraction(_file, class_name='ResizeLinearVector', count=3):
    rand = random.Random()
    l = []
    for _ in list(range(count-1)):
        a = rand.randint(-10000, 10000)
        b = rand.randint(-10000, 10000)
        l.append([a, b])
    _file.write('assertEquals((new '+class_name+'<Fraction>(new Fraction[]{new Fraction(')
    for a, b in l:
        _file.write(str(a))
        _file.write(',')
        _file.write(str(b))
        _file.write('),new Fraction(')
    a1 = rand.randint(-10000, 10000)
    b1 = rand.randint(-10000, 10000)
    _file.write(str(a1))
    _file.write(',')
    _file.write(str(b1))
    _file.write(')})).negate(),new '+class_name+'<Fraction>(new Fraction[]{new Fraction(')
    for a, b in l:
        _file.write(str(-a))
        _file.write(',')
        _file.write(str(b))
        _file.write('),new Fraction(')
    _file.write(str(-a1))
    _file.write(',')
    _file.write(str(b1))
    _file.write(')}));\n')


file = open('test.txt', 'w')
for _ in list(range(10)):
    gen_resize_linear_vector_negate_test_with_fraction(file)