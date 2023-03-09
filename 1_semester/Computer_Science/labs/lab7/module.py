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