import sys
import os

if (len (sys.argv)) == 1:

path = sys.argv[1]
if path[0] == '-':
    option = path
    path = sys.argv[2]
print(path)
print(option)