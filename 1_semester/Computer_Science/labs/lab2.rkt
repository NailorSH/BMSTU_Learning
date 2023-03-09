;; ЛР 2

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