;;ЛР 1

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