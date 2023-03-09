% Лабораторная работа № 1. Основы ЯП Scheme и среды разработки DrRacket
% 25 сентября 2022 г.
% Наиль Шиятов, ИУ9-11Б

# Цель работы
Приобретение навыков работы с основами программирования на языке Scheme в среде DrRacket.

# Реализация
```scheme
;; 1
(define (signum x1)
  (if (> x1 0) 1
      (if (= x1 0) 0 -1)))

(define (my-abs x)
  ((if (> x 0) + -) x))

;; 2
(define (my-odd? n) (= (remainder n 2) 1))

(define (my-even? n) (= (remainder n 2) 0))

;; 3
(define (power b e)
  (if (= e 1) b
      (* b (power b (- e 1)))))

(define (fact n)
  (if (> n 1)
      (* n (fact (- n 1)))
      n))
```
# Тестирование
```
Welcome to DrRacket, version 8.6 [cs].
Language: R5RS; memory limit: 128 MB.
> (signum -5)
-1
> (signum 6)
1
> (signum 0)
0
> (my-odd? 5)
#t
> (my-even? 5)
#f
> (power 2 4)
16
> (fact 4)
24
```
# Вывод
Я научился использовать основные элементы языка Scheme в среде DrRacket, а также 
освоил применение хвостовой рекурсии