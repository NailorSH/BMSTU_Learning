% Лабораторная работа № 3. Типы данных. Модульное тестирование DrRacket
% 25 октября 2022 г.
% Наиль Шиятов, ИУ9-11Б

# Цель работы
+   На практике ознакомиться с системой типов языка Scheme.
+   На практике ознакомиться с юнит-тестированием. 
+   Разработать свои средства отладки программ на языке Scheme.
+   На практике ознакомиться со средствами метапрограммирования языка Scheme.

# Реализация
```scheme
;; ~~~~~~~~~~~~~~ 1 ~~~~~~~~~~~~~~
(define-syntax trace-ex
  (syntax-rules()
    ((trace expr)
     (begin
       (write 'expr)
       (display " => ")
       (let* ((expr-res expr))
         (write expr-res)
         (newline)
         expr-res)))))
 

;; example
(define (zip . xss)
  (if (or (null? xss)
          (null? (trace-ex (car xss))))
      '()
      (cons (map car xss)
            (apply zip (map cdr (trace-ex xss))))))

;;example
(define counter 0)
(define (next)
  (set! counter (+ 1 counter))
  counter)



;; ~~~~~~~~~~~~~~ 2 ~~~~~~~~~~~~~~
(define-syntax test
  (syntax-rules()
    ((test expr true-res)
     (list 'expr (lambda () expr) true-res))))

(define (run-test the-test)
  (let* ((expr (car the-test))
         (_ (write expr))
         (expr-res ((car (cdr the-test))))
         (true-res (car (cdr (cdr the-test)))))
    (if (equal? expr-res true-res)
        (begin
          (display " ok")
          (newline)
          #t)
        (begin
          (display " FAIL")
          (newline)
          (display "  Expected: ")
          (write true-res)
          (newline)
          (display "  Returned: ")
          (write expr-res)
          (newline)
          #f))))

(define (run-tests the-tests)
  (or (null? the-tests)
      (if (run-test (car the-tests))
          (run-tests (cdr the-tests))
          (begin
            (run-tests (cdr the-tests))
            #f))))

;; examples
(define (signum x)
  (cond
    ((< x 0) -1)
    ((= x 0)  1) ; Ошибка здесь!
    (else     1)))

(define signum-tests
  (list (test (signum -2) -1)
        (test (signum  0)  0)
        (test (signum  2)  1)))

(set! counter 1)
(define next-tests
  (list (test (next) 2)
        (test (next) 3)
        (test (next) 4)))

(run-tests signum-tests)



;; ~~~~~~~~~~~~~~ 3 ~~~~~~~~~~~~~~
(define (list-insert xs index item)
  (cond
    ((equal? index 0) (cons item xs))
    ((equal? index (- (length xs) 1)) (cons xs item))
    (else
     (cons (car xs) (list-insert (cdr xs) (- index 1) item)))))

(define (vector-insert xs index item)
  (list->vector (list-insert (vector->list xs) index item)))

(define (string-insert xs index item)
  (cond
    ((> index (string-length xs)) #f)
    (else (if (string? item)
              (string-append (substring xs 0 index) item (substring xs index (string-length xs)))
              (string-append (substring xs 0 index) (make-string 1 item) (substring xs index (string-length xs)))))))

(define-syntax ref
  (syntax-rules()
    ((ref xs index)
     (cond
       ((null? xs) #f)
       ((list? xs) (if (or (>= index (length xs)) (< index 0))
                       #f
                       (list-ref xs index)))
       ((vector? xs) (if (or (>= index (vector-length xs)) (< index 0))
                         #f
                         (vector-ref xs index)))
       ((string? xs) (if (or (>= index (string-length xs)) (< index 0))
                         #f
                         (string-ref xs index)))
       (else #f)))

    ((ref xs index item)
     (cond
       ((null? xs) #f)
       ((list? xs) (if (or (> index (length xs)) (< index 0))
                       #f
                       (list-insert xs index item)))
       ((vector? xs) (if (or (> index (vector-length xs)) (< index 0))
                         #f
                         (vector-insert xs index item)))
       ((string? xs) (if (or (> index (string-length xs)) (< index 0))
                         #f
                         (if (or(string? item) (char? item))
                             (string-insert xs index item)
                             #f)))
       (else #f)))))



;; ~~~~~~~~~~~~~~ 4 ~~~~~~~~~~~~~~
(define (factorize f)
  (let* ((op (car f))
         (x (cadr (cadr f)))
         (y (cadr (caddr f)))
         (deg (list-ref (list-ref f 1) 2)))
    (case op
      ('+ (case deg
            ('3 (list '+
                      (list 'expt x 3)
                      (list '* 3 (list 'expt x 2) y)
                      (list '* 3 x (list 'expt y 2))
                      (list 'expt y 3)))))
      ('- (case deg
            ('2 (list '* (list '- x y) (list '+ x y)))
            ('3 (list '+
                      (list 'expt x 3)
                      (list '- (list '* 3 (list 'expt x 2) y))
                      (list '* 3 x (list 'expt y 2))
                      (list '- (list 'expt y 3)))))))))
```
# Тестирование
```
Welcome to DrRacket, version 8.6 [cs].
Language: R5RS; memory limit: 128 MB.
(signum -2) ok
(signum 0) FAIL
  Expected: 0
  Returned: 1
(signum 2) ok
#f
> (zip '(1 2 3) '(one two three))
(car xss) => (1 2 3)
xss => ((1 2 3) (one two three))
(car xss) => (2 3)
xss => ((2 3) (two three))
(car xss) => (3)
xss => ((3) (three))
(car xss) => ()
((1 one) (2 two) (3 three))
> (ref '(1 2 3) 1)
2
> (ref #(1 2 3) 1)
2
> (ref "123" 1)
#\2
> (ref '(1 2 3) 1 0)
(1 0 2 3)
> (ref #(1 2 3) 1 0)
#(1 0 2 3)
> (ref #(1 2 3) 1 #\0)
#(1 #\0 2 3)
> (ref "123" 1 #\0)
"1023"
> (ref "123" 1 0)
#f
> (ref "123" 3 #\4)
"1234"
> (ref "123" 5 #\4)
#f
> (factorize '(- (expt x 2) (expt y 2)))
(* (- x y) (+ x y))
> (factorize '(- (expt (+ first 1) 2) (expt (- second 1) 2)))
(* (- (+ first 1) (- second 1)) (+ (+ first 1) (- second 1)))
> (eval (list (list 'lambda 
                      '(x y) 
                      (factorize '(- (expt x 2) (expt y 2))))
                1 2)
          (interaction-environment))
-3
```
# Вывод
Я научился определять макросы и работать с ними, познакомился с юнит-тестированием и научился писать свои юнит-тесты для проверки работоспособности программ. Также узнал о таком способе отладки как трассировка, смог определить свой макрос trace.