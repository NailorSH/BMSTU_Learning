% Лабораторная работа № 7. Оболочка и скрипты
% 29 декабря 2022 г.
% Наиль Шиятов, ИУ9-11Б

# Цель работы
Получение навыков написания сценариев на «скриптовых» языках.
# Реализация

***wait***
```bash
#!/bin/bash

sleep 1
echo "Completed1"
echo "Coed2"
```
***task1***
```bash
#!/bin/bash

PID=-1
while true; do
    if ! ps -p $PID > /dev/null 2>&1; then
        $1 >> output.txt 2>> error.txt &
        PID=$!
        echo "Completed"
    fi
    sleep $2m
done
```
***task2***
```bash
#!/bin/bash

C_STRINGS="$((find $1 -name '*.c' -print0 | xargs -0 cat ) | sed '/^\s*$/d' | wc -l)"
H_STINGS="$((find $1 -name '*.h' -print0 | xargs -0 cat ) | sed '/^\s*$/d' | wc -l)"
SUM=$(($C_STRINGS+$H_STINGS))
echo "${SUM}"
```
***module.py***
```python
import random
import time

def current_milli_time():
    return round(time.time() * 1000)

random.seed(current_milli_time)

symbols = "`1234567890-=qwertyuiop[]asdfghjkl;'\zxcvbnm,./!@#$%^&*()_+QWERTYUIOPASDFGHJKL:|ZXCVBNM<>?"

def random_string(n):
    s = ""
    for i in range(n):
        s += symbols[random.randint(0,len(symbols) - 1)]
    return s
```
***task3***
```python
#!/usr/bin/python3
import sys
from module import *

length = int(sys.argv[1])
number = int(sys.argv[2])

for i in range(number):
    print(random_string(length))
```

# Тестирование
```
nailorsh@DESKTOP-UQBCQHD:/mnt/d/files/education/univer/learning/1_semester
/computer_science/labs/lab7$ ./task1 wait 0.005
Completed
Completed
Completed
Completed
# ...
Completed
^C

nailorsh@DESKTOP-UQBCQHD:/mnt/d/files/education/univer/learning/1_semester
/computer_science/labs/lab7$ ./task2 Algorithms
345

nailorsh@DESKTOP-UQBCQHD:/mnt/d/files/education/univer/learning/1_semester
/computer_science/labs/lab7$ ./task3 5 6
@Tx&>
JOk7*
-8+St
Zv^lI
35c;u
-H=5b
```
# Вывод
Я научился пользоваться интерпретатором bash, освоил стандартные команды оболочки.
Изучил и применил при выполнении заданий различные команды для управления процессами.
Освоил написание скриптов для bash и применил в заданиях функции для обработки текста.
Повторил основы синтаксиса языка Python, научился получать и обрабатывать аргументы,
передаваемые через терминал. Научился подключать модуль в скрипт