;; (sort ">" list)
; insertion sort
(define (part x y)
  (if (eqv? (car x) y)
      (cdr x)
      (cons (car x) (part (cdr x) y))))
 
(define (sort ord x)
  (if (null? x)
      x
      (cond ((equal? ord ">")
             (cons (apply max x) (sort ">" (part x (apply max x)))))
            ((equal? ord "<")
             (cons (apply min x) (sort "<" (part x (apply min x)))))
            (else
             (display "ERROR: the order symbol can be either \">\" or \"<\"")))))