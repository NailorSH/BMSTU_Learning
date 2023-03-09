% Лабораторная работа № 2. Рекурсия, процедуры высшего порядка, обработка списков
% 27 сентября 2022 г.
% Наиль Шиятов , ИУ9-11Б

# Цель работы
Приобретение навыков работы с основами программирования на языке Scheme: 
использование рекурсии, процедур высшего порядка, списков.
# Реализация
```scheme
;; 1
(define (count x xs)
  (define (loop q x xs)
    (if (null? xs)
        q
        (if (equal? x (car xs))
            (loop(+ q 1) x (cdr xs))
            (loop q x (cdr xs)))))
  (loop 0 x xs))


;; 2
(define (delete pred? xs)
  (define (loop pred? xs xs-ans)
    (if (null? xs)
        xs-ans
        (if (pred? (car xs))
            (loop pred? (cdr xs) xs-ans)
            (loop pred? (cdr xs) (append xs-ans (list(car xs)))))))
  (loop pred? xs '()))


;; 3
(define (iterate f x n)
  (define (loop f xs n)
    (if (= n 0)
        xs
        (loop f (cons x (map f xs)) (- n 1))))
  (if (= n 0)
     '()
     (loop f (list x) (- n 1))))


;; 4
(define (intersperse e xs)
  (define (loop e xs xs-ans)
    (if (or (null? xs) (= (length xs) 1))
        (append xs-ans xs)
        (loop e (cdr xs) (append xs-ans (list (car xs)) (list e)))))
  (loop e xs (list)))


;; 5
(define (any? pred? xs)
  (and (not (= (length xs) 0))
       (or (pred? (car xs)) (any? pred? (cdr xs)))))
 
(define (all? pred? xs)
  (or (= (length xs) 0)
      (and (pred? (car xs)) (all? pred? (cdr xs)))))


;; 6
(define (f x) (+ x 2))
(define (g x) (* x 3))
(define (h x) (- x)) ;; f, g, h for test
 
(define (o . l)
  (define (razvorot xs)
    (define (loop xs xs-ans)
      (if (null? xs)
          xs-ans
          (loop (cdr xs) (append (list (car xs)) xs-ans))))
    (loop xs '()))
  (define (comp-test x) 
    (define (comp x l)
      (if (= (length l) 0)
          x
          (comp ((car l) x) (cdr l))))
    (comp x (razvorot l)))
  comp-test)
```
# Тестирование
```
Welcome to DrRacket, version 8.6 [cs].
Language: R5RS; memory limit: 128 MB.
> (count 'a '(a b c a))
2
> (count 'b '(a c d))
0
> (count 'a '())
0
> (delete even? '(0 1 2 3))
(1 3)
> (delete even? '(0 2 4 6))
()
> (delete even? '(1 3 5 7))
(1 3 5 7)
> (delete even? '())
()
> (iterate (lambda (x) (* 2 x)) 1 6)
(1 2 4 8 16 32)
> (iterate (lambda (x) (* 2 x)) 1 1)
(1)
> (iterate (lambda (x) (* 2 x)) 1 0)
()
> (intersperse 'x '(1 2 3 4))
(1 x 2 x 3 x 4)
> (intersperse 'x '(1 2))
(1 x 2)
> (intersperse 'x '(1))
(1)
> (intersperse 'x '())
()
> (any? odd? '(1 3 5 7))
#t
> (any? odd? '(0 1 2 3))
#t
> (any? odd? '(0 2 4 6))
#f
> (any? odd? '())
#f
> (all? odd? '(1 3 5 7))
#t
> (all? odd? '(0 1 2 3))
#f
> (all? odd? '(0 2 4 6))
#f
> (all? odd? '())
#t
> ((o f g h) 1)
-1
> ((o f g) 1)
5
> ((o h) 1)
-1
> ((o) 1)
1
```
# Вывод
Научился использовать процедуры высшего порядка, работать со списками и реализовывать
функции для различных операций со списками. Также освоил, как реализовать композицию 
функций.